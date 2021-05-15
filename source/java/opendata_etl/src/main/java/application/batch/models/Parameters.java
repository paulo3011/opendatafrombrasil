package application.batch.models;

import lombok.Getter;
import lombok.Setter;
import com.beust.jcommander.Parameter;

/**
 * Batch Application parameters
 */
public class Parameters {
    @Getter
    @Setter
    @Parameter
    private String outputPath;

    public Parameters(){}
    public Parameters(String[] argv){
        //this.parse(argv);
        System.out.println("Parameters not implemented yet");
    }
}
