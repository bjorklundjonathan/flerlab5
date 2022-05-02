import java.math.BigInteger;

import rsa.Factorizer;
import rsa.ProgressTracker;

public class CodeBreakingExample {

    public static void main(String[] args) throws InterruptedException {

        // 'N' from a public key, and an encrypted message.
        BigInteger n = new BigInteger("217158619938165709");
        String ciphertext = "59gp9p5qfd1-14z7kbsnuo1g-76btlfh7wwq-12po84bg8dvl-drtiw0a91nt-4ooagwi5kxh-k1o2l6iyyvm-14wbyot0llud-1d2rd5vcoczr";

        ProgressTracker tracker = new Tracker();
        String plaintext = Factorizer.crack(ciphertext, n, tracker);

        System.out.println("\nDecryption complete. The message is:\n\n  " + plaintext);
    }

    // -----------------------------------------------------------------------

    /** ProgressTracker: reports how far factorization has progressed */ 
    private static class Tracker implements ProgressTracker {
        private int totalProgress = 0;

        /**
         * Called by Factorizer to indicate progress. The total sum of
         * ppmDelta from all calls will add upp to 1000000 (one million).
         * 
         * @param  ppmDelta   portion of work done since last call,
         *                    measured in ppm (parts per million)
         */
        @Override
        public void onProgress(int ppmDelta) {
            totalProgress += ppmDelta;
            System.out.println("progress = " + totalProgress + "/1000000");
        }
    }
}
