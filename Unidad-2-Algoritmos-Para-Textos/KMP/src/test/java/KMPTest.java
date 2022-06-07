import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class KMPTest {
    @Test
    void KMPTest(){
        int[] abracadabraKMP = KMP.nextComputation("abracadabra".toCharArray());
        Assertions.assertEquals(abracadabraKMP[0], 0);
        Assertions.assertEquals(abracadabraKMP[1], 0);
        Assertions.assertEquals(abracadabraKMP[2], 0);
        Assertions.assertEquals(abracadabraKMP[3], 1);
        Assertions.assertEquals(abracadabraKMP[4], 0);
        Assertions.assertEquals(abracadabraKMP[5], 1);
        Assertions.assertEquals(abracadabraKMP[6], 0);
        Assertions.assertEquals(abracadabraKMP[7], 1);
        Assertions.assertEquals(abracadabraKMP[8], 2);
        Assertions.assertEquals(abracadabraKMP[9], 3);
        Assertions.assertEquals(abracadabraKMP[10], 4);
    }
    @Test
    void findAllTest(){
        ArrayList<Integer> ans1 = new ArrayList<>();
        ans1.add(2); ans1.add(17); ans1.add(29);
        Assertions.assertEquals(ans1, KMP.findAll(
                "no".toCharArray(),"sino se los digo no se si es nocivo".toCharArray()));
        Assertions.assertEquals(new ArrayList<Integer>(), KMP.findAll(
                "ni".toCharArray(),"sino se los digo no se si es nocivo".toCharArray()));
        ArrayList<Integer> ans2 = new ArrayList<>();
        ans2.add(0); ans2.add(4); ans2.add(5); ans2.add(6);
        Assertions.assertEquals(ans2, KMP.findAll(
                "aaa".toCharArray(),"aaabaaaaab".toCharArray()));
    }
}
