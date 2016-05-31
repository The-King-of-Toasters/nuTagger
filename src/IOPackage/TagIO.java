/* 
 * Copyright (C) 2016 Stephen Gregoratto.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package IOPackage;

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.exceptions.CannotReadException;
import entagged.audioformats.exceptions.CannotWriteException;
import java.io.File;

/**
 *
 * @author Stephen Gregoratto, 2016
 */
public class TagIO {

    /**
     * Music tags are read from the called file and returned as a string array
     * <br><br>
     * The Array's data is as follows:
     * <br>
     * <code> Place[0] = Song Title/Name (e.g. "Neon Lights") </code><br>
     * <code> Place[1] = Album Name (e.g. "The Man Machine") </code><br>
     * <code> Place[2] = Artist Name(e.g "Kraftwerk") </code><br>
     * <code> Place[3] = Song Year (e.g. "1977") </code><br>
     * <code> Place[4] = Song Genre (e.g. "Electronic") </code><br>
     * <code> Place[5] = Song's Comment (e.g. "A good song!") </code><br>
     * <code> Place[6] = TBD </code><br>
     * <code> Place[7] = TBD </code><br>
     *
     *
     * @param PickedFile
     * @return String Tags[]
     * @throws entagged.audioformats.exceptions.CannotReadException
     */
    public static String[] GetTagsInFile(File PickedFile) throws CannotReadException {
        AudioFile song = AudioFileIO.read(PickedFile);
        String[] Meta = new String[6];
        Meta[0] = song.getTag().getTitle().toString();
        Meta[1] = song.getTag().getAlbum().toString();
        Meta[2] = song.getTag().getArtist().toString();
        Meta[3] = song.getTag().getYear().toString();
        Meta[4] = song.getTag().getGenre().toString();
        Meta[5] = song.getTag().getComment().toString();

        // Removing List brackets around the strings
        char rm[] = {'[', ']'};
        for (char c : rm) {
            for (int i = 0; i <= (Meta.length - 1); i++) {
                Meta[i] = Meta[i].replace("" + c, "");
            }
        }
        return Meta;
    }

    /**
     * Overwrites PickedFile's metadata with the values in Metadata string
     * array.
     * <br>
     * The values in the array must correspond to those in {@code GetTagsInFile()
     * }.
     *
     * @param String[5] Metadata
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

        try {
            AudioFileIO.write(song);
            return 0;
        } catch (CannotWriteException ex) {
            System.err.println(ex);
            return -1;
        }

    }
}
