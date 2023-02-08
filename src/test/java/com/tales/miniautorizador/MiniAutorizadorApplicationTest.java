package com.tales.miniautorizador;

import com.tales.miniautorizador.utils.AutorizadorConstants;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest()
public class MiniAutorizadorApplicationTest {
    public static final long ZERO = 0L;
    @Test
    public void main() {
        MiniAutorizadorApplication.main(new String[] {});
    }

    @Test
    public void toCoverage(){
        AutorizadorConstants constants = new AutorizadorConstants();
        Assert.assertEquals(ZERO, constants.ZERO);
    }
}
