package ca.warp7.rt.exec;

import ca.warp7.rt.core.Launcher;
import kb.service.abc.ABC;

public class ResTableABC implements ABC {
    @Override
    public void launch(String[] args) {
        Launcher.main(args);
    }

    public static void main(String[] args) {
        Launcher.main(args);
    }
}
