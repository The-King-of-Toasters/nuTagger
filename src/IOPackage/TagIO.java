/* 
 * The MIT License
 *
 * Copyright 2016 Stephen Gregoratto.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package IOPackage;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.exceptions.CannotWriteException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephen Gregoratto, 2016
 */
public class TagIO {
    
    /**
     * Music tags are read from the called file with metadata returned as a
     * string array
     * <br><br>
     * The Array's data is as follows
     * <br>
     * <code>:
     *          Place[0] = Song Title/Name (e.g. "Neon Lights") <br>
     *          Place[1] = Album Name (e.g. "The Man Machine") <br>
     *          Place[2] = Artist Name(e.g "Kraftwerk") <br>
     *          Place[3] = Song Year (e.g. "1977") <br>
     *          Place[4] = Song Genre (e.g. "Electronic") <br>
     *          Place[5] = Song's Comment (e.g. "A good song!") <br>
     * </code>
     *
     *
     * @param PickedFile
     * @return String Tags[6]
     * @throws entagged.audioformats.exceptions.CannotReadException
     */
    public static String[] GetTagsInFile(File PickedFile) throws CannotReadException {
        AudioFile song = AudioFileIO.read(PickedFile);
        String[] Tags = new String[6];

        Tags[0] = song.getTag().getTitle().toString();
        Tags[1] = song.getTag().getAlbum().toString();
        Tags[2] = song.getTag().getArtist().toString();
        Tags[3] = song.getTag().getYear().toString();
        Tags[4] = song.getTag().getGenre().toString();
        Tags[5] = song.getTag().getComment().toString(); //.substring(9);

        /* Removing List brackets around the strings */
        for (int i = 0; i <= (Tags.length - 1); i++) {
            /* Substring starts after first bracket, ends before last bracket */
            Tags[i] = Tags[i].substring(1, (Tags[i].length() - 1));
        }
        
        return Tags;
    }
    

    /**
     * Overwrites PickedFile's metadata with the values in the Metadata string
     * array.
     * <br>
     * The values in the array must correspond to those in {@code GetTagsInFile() }.
     *
     * @param NewTags[]
     * @param PickedFile
     * @return 0 (Write) or -1 (Cannot Write)
     * @throws entagged.audioformats.exceptions.CannotReadException
     */
    public static int WriteNewTagsToFile(String[] NewTags, File PickedFile) throws CannotReadException {
        AudioFile song = AudioFileIO.read(PickedFile);

        song.getTag().setTitle(NewTags[0]);
        song.getTag().setAlbum(NewTags[1]);
        song.getTag().setArtist(NewTags[2]);
        song.getTag().setYear(NewTags[3]);
        song.getTag().setGenre(NewTags[4]);
        song.getTag().setComment(NewTags[5]);
        try {
            AudioFileIO.write(song);
            return 0;
        } catch (CannotWriteException ex) {
            Logger.getLogger(IOPackage.TagIO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }
}
