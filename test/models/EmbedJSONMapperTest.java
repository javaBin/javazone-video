package models;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import models.domain.Embed;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

/**
 * User: Knut Haugen <knuthaug@gmail.com>
 * 2011-10-07
 */
public class EmbedJSONMapperTest {

    @Test
    public void embedCodeHasembedCode() throws IOException {
        String videosJson = Files.toString(new File("test/testdata/embed.json"), Charsets.UTF_8);
        EmbedJSONMapper mapper = new EmbedJSONMapper(videosJson);
        Embed embed = mapper.getEmbed();

        assertEquals("<iframe src=\"http://player.vimeo.com/video/28769417\" width=\"1280\" height=\"480\" frameborder=\"0\" webkitAllowFullScreen allowFullScreen></iframe>",
                embed.code());
    }

}
