package org.ndrwdn.mbgradle;

import org.gradle.api.Project;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.ndrwdn.mbgradle.acquisition.Acquire;

import java.io.File;

public class AcquireTest {

    public interface MountebankProject extends Project {
        MountebankPluginExtension getMountebank();
    }

//    @Test // TODO:
    public void testAcquire() {
        MountebankProject p = Mockito.mock(MountebankProject.class);
        Mockito.when(p.getBuildDir()).thenReturn(new File("/Users/hayde/IdeaProjects/mountebank-gradle"));
        Mockito.when(p.getProjectDir()).thenReturn(new File("/Users/hayde/IdeaProjects/mountebank-gradle"));
        Mockito.when(p.getMountebank()).thenReturn(new MountebankPluginExtension());
        Acquire a = new Acquire(p);
        a.acquire();
    }

}
