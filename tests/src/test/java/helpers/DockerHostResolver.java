package helpers;

public class DockerHostResolver {
    public static String getDockerHost() {
        if(System.getProperty("os.name").contains("Windows")){
            return "host.docker.internal";
        }

        return "172.17.0.1";
    }
}
