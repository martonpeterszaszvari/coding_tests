# Test 4 — Shuffle Playlist (Java 21)

Open this folder as a Maven project in IntelliJ and set JDK 21.
Run `src/main/java/Main.java` using the green ▶ button. No external libraries required.

**Your edit location:** `src/main/java/Result.java` in `getShuffledPlaylist(...)`.
The stub intentionally returns an empty list.

At each output position: choose the eligible artist with the most remaining unplayed songs;
break ties by first occurrence in the original playlist. An artist cannot immediately repeat
if another artist has songs left. Preserve original relative order for each artist's songs.

Test inputs: `src/main/resources/inputs/case01.txt` ... `case05.txt`.
Corresponding expected outputs: `src/main/resources/expected/case01.txt` ... `case05.txt`.
Each line after the initial song count contains one `title artist` pair separated by spaces.
Titles/artists consist of alphanumeric characters, so the pair format is unambiguous.

The test program reports only PASS/FAIL, with no hints or solutions.
