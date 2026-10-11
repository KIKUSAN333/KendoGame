import java.io.*;
import java.nio.charset.StandardCharsets;

public class InputSmokeTest {
    public static void main(String[] args) throws Exception {
        PrintStream original = System.out;
        System.setOut(new PrintStream(OutputStream.nullOutputStream()));
        try {
            Model model = new Model();
            model.process("s");
            model.process("s");
            model.process("f");
            if (!model.getStage().getIsTraining() || model.getMenu().getInMenu())
                throw new AssertionError("Training selection failed");
            model.process("");
            model.process(null);
            int x = model.getPlayer().getX();
            model.process("d");
            if (model.getPlayer().getX() != x + 1) throw new AssertionError("Movement failed");
            model.process("j");
            if (model.getPlayer().getMotionID() != Character.ATTACK11_ID)
                throw new AssertionError("Attack failed");
            model.process("q");
            if (!model.getMenu().getInMenu()) throw new AssertionError("Return failed");
            System.setIn(new ByteArrayInputStream("s\ns\nf\n\nd\nq\n".getBytes(StandardCharsets.UTF_8)));
            Model inputModel = new Model();
            inputModel.run();
            if (!inputModel.getMenu().getInMenu() || inputModel.getPlayer().getX() != x + 1)
                throw new AssertionError("Controller input failed");
        } finally {
            System.setOut(original);
        }
        System.out.println("PASS: menu, training, empty input, movement, attack, controller input and EOF shutdown");
    }
}
