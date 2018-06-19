package ru.jihor;

import io.github.swagger2markup.Swagger2MarkupConverter;
import org.asciidoctor.Asciidoctor;
import org.asciidoctor.OptionsBuilder;
import org.asciidoctor.SafeMode;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class does exactly the same job as `./gradlew asciidoc`
 *
 * @author jihor (dmitriy_zhikharev@rgs.ru)
 * Created on 2018-04-24
 */
public class Swagger2Adoc2Pdf {
    public static void main(String[] args) throws Exception {
        Path localSwaggerFile = Paths.get("src/docs/openapi/api.json");
        Path asciidocOutputDirectory = Paths.get("src/docs/asciidoc");

        Swagger2MarkupConverter.from(localSwaggerFile)
                               .build()
                               .toFolder(asciidocOutputDirectory);

        File input = Paths.get("src/docs/asciidoc/definitions.adoc").toFile();

        Path outputPath = Paths.get("src/docs/pdf/definitions.pdf");
        Files.createDirectories(outputPath.getParent());
        File output = outputPath.toFile();

        Asciidoctor.Factory
                .create()
                .convertFile(input, OptionsBuilder.options().backend("pdf").toFile(output).safe(SafeMode.UNSAFE).get());
    }
}
