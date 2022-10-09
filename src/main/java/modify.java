
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class modify {
  public static void main(String[] args) throws IOException, ParseException {
    JSONParser jsonparser = new JSONParser();
    Object changesobj = jsonparser.parse(new FileReader(".\\jsonfiles\\changes.json"));
    Object mixtapeobj = jsonparser.parse(new FileReader(".\\jsonfiles\\mixtape.json"));

    JSONArray changes = (JSONArray)changesobj;
    JSONObject mixtape = (JSONObject) mixtapeobj;
    //list of user object
    JSONArray users = (JSONArray)mixtape.get("users");
    //list of playlist object
    JSONArray playlists = (JSONArray)mixtape.get("playlists");
    //list of song object
    JSONArray songs = (JSONArray)mixtape.get("songs");
    List<Integer> removed_playlist_id = new ArrayList<>();
    for(int i = 0; i < changes.size();i++) {
      String type = (String)((JSONObject)changes.get(i)).get("type");
      if(type.equals("add_song_to_playlist")) {
        int playlistid = Integer.valueOf((String)((JSONObject)changes.get(i)).get("playlist_id"));
        int addsong_id = Integer.valueOf((String)((JSONObject)changes.get(i)).get("song_id"));
        JSONArray song_array = (JSONArray)((JSONObject)playlists.get(playlistid-1)).get("song_ids");
        song_array.add(addsong_id+"");
      } else if(type.equals("add_playlist")) {
        JSONObject newplaylist = new JSONObject();
        if(removed_playlist_id.size() != 0) {
          newplaylist.put("id",removed_playlist_id.get(0)+"");
        } else {
          newplaylist.put("id", playlists.size()+1+"");
        }
        newplaylist.put("owner_id",(String)((JSONObject)changes.get(i)).get("user_id"));
        newplaylist.put("song_ids",((JSONObject)changes.get(i)).get("song_ids"));
        playlists.add(newplaylist);
      } else if(type.equals("remove_playlist")) {
        String playlistid = (String)((JSONObject)changes.get(i)).get("playlist_id");
        removed_playlist_id.add(Integer.valueOf(playlistid));
        playlists.remove(Integer.valueOf(playlistid)-1);
      }

    }
    // write results to output.json
    try(FileWriter file = new FileWriter(".\\jsonfiles\\output.json")) {
      JSONObject outputobj = new JSONObject();
      outputobj.put("users",users);
      outputobj.put("playlists",playlists);
      outputobj.put("songs",songs);
      file.write(prettyPrintJSON(outputobj.toString()));
      file.flush();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  // I found a function that pretty print json
  // source: https://stackoverflow.com/a/49564514
  public static String prettyPrintJSON(String unformattedJsonString) {
    StringBuilder prettyJSONBuilder = new StringBuilder();
    int indentLevel = 0;
    boolean inQuote = false;
    for(char charFromUnformattedJson : unformattedJsonString.toCharArray()) {
      switch(charFromUnformattedJson) {
        case '"':
          // switch the quoting status
          inQuote = !inQuote;
          prettyJSONBuilder.append(charFromUnformattedJson);
          break;
        case ' ':
          // For space: ignore the space if it is not being quoted.
          if(inQuote) {
            prettyJSONBuilder.append(charFromUnformattedJson);
          }
          break;
        case '{':
        case '[':
          // Starting a new block: increase the indent level
          prettyJSONBuilder.append(charFromUnformattedJson);
          indentLevel++;
          appendIndentedNewLine(indentLevel, prettyJSONBuilder);
          break;
        case '}':
        case ']':
          // Ending a new block; decrese the indent level
          indentLevel--;
          appendIndentedNewLine(indentLevel, prettyJSONBuilder);
          prettyJSONBuilder.append(charFromUnformattedJson);
          break;
        case ',':
          // Ending a json item; create a new line after
          prettyJSONBuilder.append(charFromUnformattedJson);
          if(!inQuote) {
            appendIndentedNewLine(indentLevel, prettyJSONBuilder);
          }
          break;
        default:
          prettyJSONBuilder.append(charFromUnformattedJson);
      }
    }
    return prettyJSONBuilder.toString();
  }

  /**
   * Print a new line with indention at the beginning of the new line.
   * @param indentLevel
   * @param stringBuilder
   */
  private static void appendIndentedNewLine(int indentLevel, StringBuilder stringBuilder) {
    stringBuilder.append("\n");
    for(int i = 0; i < indentLevel; i++) {
      // Assuming indention using 2 spaces
      stringBuilder.append("  ");
    }
  }
}
