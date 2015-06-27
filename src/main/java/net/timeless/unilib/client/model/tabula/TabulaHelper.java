package net.timeless.unilib.client.model.tabula;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author gegy1000
 */
public class TabulaHelper
{
    private static Gson gson = new Gson();

    /**
     * @param stream the input stream to the tabula model file
     * @returns the parsed tabula model
     */
    public static JsonTabulaModel parseTabulaModel(InputStream stream)
    {
        return gson.fromJson(new InputStreamReader(stream), JsonTabulaModel.class);
    }

    /**
     * @param tabulaModelFile the path to the tbl file to load (assets/example/models/fish.tbl)
     * @returns a model file that you can use in your renderer
     */
    public ModelJson parseTabulaModel(String tabulaModelFile) throws Exception {
        if(!tabulaModelFile.endsWith(".tbl"))
            tabulaModelFile += ".tbl";

        try (ZipInputStream inputStream = new ZipInputStream(TabulaHelper.class.getResourceAsStream(tabulaModelFile))) {
            ZipEntry entry;

            while ((entry = inputStream.getNextEntry()) != null) {
                if (entry.getName().equals("model.json")) {
                    JsonTabulaModel parseTabulaModel = parseTabulaModel(inputStream);

                    inputStream.close();

                    return new ModelJson(parseTabulaModel);
                }
            }

            inputStream.close();
        }

        return null;
    }

    /**
     * @param tabulaModelFile the path to the tbl file to load (assets/example/models/fish.tbl)
     * @param animator the animator for your model
     * @returns a model file that you can use in your renderer
     */
    public ModelJson parseTabulaModel(String tabulaModelFile, IModelAnimator animator) throws Exception {
        if(!tabulaModelFile.endsWith(".tbl"))
            tabulaModelFile += ".tbl";

        try (ZipInputStream inputStream = new ZipInputStream(TabulaHelper.class.getResourceAsStream(tabulaModelFile))) {
            ZipEntry entry;

            while ((entry = inputStream.getNextEntry()) != null) {
                if (entry.getName().equals("model.json")) {
                    JsonTabulaModel parseTabulaModel = parseTabulaModel(inputStream);

                    inputStream.close();

                    return new ModelJson(parseTabulaModel, animator);
                }
            }

            inputStream.close();
        }

        return null;
    }
}
