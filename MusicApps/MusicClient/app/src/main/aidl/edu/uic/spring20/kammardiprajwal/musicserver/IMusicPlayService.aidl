// IMusicPlayService.aidl
package edu.uic.spring20.kammardiprajwal.musicserver;
import edu.uic.spring20.kammardiprajwal.musicserver.MusicModel;
// Declare any non-default types here with import statements

interface IMusicPlayService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String connectFlag();
    List<MusicModel> getAll();
    MusicModel getSongInfo(int songNo);
    String getURL(int songNo);
}
