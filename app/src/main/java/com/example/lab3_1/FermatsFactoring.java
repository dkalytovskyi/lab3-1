package com.example.lab3_1;

import java.math.*;
import java.util.*;

public class FermatsFactoring {

    private final BigInteger one = BigInteger.ONE;

    public FermatsFactoring() {
    }

    /**
     * повертає n якщо √n ціле число
     * повертає 2 якщо число парне
     */
    public BigInteger factoring(BigInteger n) throws InterruptedException {
        Thread.sleep(5000); //симулюємо довге виконання задачі
        BigInteger two = new BigInteger("2");

        if (Integer.valueOf(n.toString()) % 2 == 0) {
            return two;
        } else if (Math.sqrt(Double.valueOf(n.toString())) % 1 == 0) {
            return n;
        }

        BigInteger x = this.decimalSQRT(n);
        BigInteger delta = x.shiftLeft(1).add(one);
        BigInteger q = x.multiply(x); // q = x2
        BigInteger y = one;
        do {
            q = q.add(delta);
            delta = delta.add(two);
            y = this.integerSQRT(q.subtract(n));
        } while (y.compareTo(BigInteger.ZERO) == 0);
        return this.integerSQRT(q).subtract(y).gcd(n); // НСД
    }

    /**
     * метод Герона
     * повертає цілочисельний √n заокруглений до найближчого меншого цілого числа
     */
    private BigInteger decimalSQRT(BigInteger n) {
        BigDecimal two = new BigDecimal("2");
        BigDecimal n05 = new BigDecimal("0.5");
        BigDecimal a = new BigDecimal(n);
        BigDecimal x = new BigDecimal(new BigInteger(n.bitLength() / 2, new Random()));
        while (x.multiply(x).toBigInteger().compareTo(a.toBigInteger()) != 0) {
            if (x.multiply(x).add(n05).toBigInteger().compareTo(a.toBigInteger()) != 0) {
                break;
            }
            x = x.add(a.divide(x, 10, BigDecimal.ROUND_HALF_EVEN)).divide(two, 10, BigDecimal.ROUND_HALF_EVEN);
        }
        BigInteger result = x.toBigInteger();
        while (result.multiply(result).compareTo(n) > 0) {
            result = result.subtract(one);
        }
        return result;
    }

    /**
     * перевірка на цілочисельний квадрат
     */
    private BigInteger integerSQRT(BigInteger q) {
        int bits = q.getLowestSetBit();
        if ((bits % 2) == 1) {
            return BigInteger.ZERO;
        }
        q = q.shiftRight(bits);
        q = q.subtract(one);
        if (q.compareTo(BigInteger.ZERO) == 0) {
            return one.shiftLeft(bits / 2);
        }
        if (q.getLowestSetBit() < 3) {
            return BigInteger.ZERO;
        }
        q = q.shiftRight(2);
        BigInteger p = q;
        BigInteger b = one;
        BigInteger a = new BigInteger("4");
        while (p.compareTo(a) >= 0) {
            p = p.shiftRight(1);
            if (p.getLowestSetBit() == 0) {
                p = p.subtract(a.shiftRight(1)).subtract(b);
                b = b.add(a);
            }
            a = a.shiftLeft(1);
        }
        if (p.compareTo(BigInteger.ZERO) == 0) {
            return b.shiftLeft(bits / 2);
        }
        if (a.compareTo(b.shiftLeft(1).add(p)) == 0) {
            return a.subtract(b).shiftLeft(bits / 2);
        }
        if (p.compareTo(a.subtract(b).shiftLeft(2)) == 0) {
            return a.shiftLeft(1).subtract(b).shiftLeft(bits / 2);
        }
        return BigInteger.ZERO;
    }
}
