package ciscospark.api;

import java.net.URI;

public class SparkBuilder {

    //Classe per costruzione api spark aggiunta alle api fornite da cisco spark
    private static final String accessToken = "ZGNlYmRiN2YtMDM3YS00NzA5LTk0MmYtMDVkNDE4MzFjNDg2MzUwZWU4ZjctNDA1";

    // SparkBuilder.getIstance().getSpark()
    private static final Spark spark = Spark.builder()
            .baseUrl(URI.create("https://api.ciscospark.com/v1"))
            .accessToken(accessToken)
            .build();

    private static SparkBuilder ourInstance = new SparkBuilder();

    public static Spark getSpark() {
        return spark;
    }

    public static SparkBuilder getInstance() {
        return ourInstance;
    }


    private SparkBuilder() {
    }


}
