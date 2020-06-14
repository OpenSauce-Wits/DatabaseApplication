package com.example.databaseapplication;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainActivityUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    ConverterUtil converterUtil = new ConverterUtil();
    float x=100, y=10;
    @Test
    public void add(){
        float actual = converterUtil.add(x,y);
        float expected= x+y;
        assertEquals("Addition failed",expected,actual,0.001);
    }
    @Test
    public void subtract(){
        float actual = converterUtil.subtract(x,y);
        float expected= x-y;
        assertEquals("Subtraction failed",expected,actual,0.001);
    }
    @Test
    public void multiply(){
        float actual = converterUtil.multiply(x,y);
        float expected= x*y;
        assertEquals("Multiplication failed",expected,actual,0.001);
    }
    @Test
    public void divide(){
        float actual = converterUtil.divide(x,y);
        float expected= x/y;
        assertEquals("Division failed",expected,actual,0.001);
    }

}