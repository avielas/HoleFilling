import java.util.Set;

public interface IHoleFillingCalculator {
    static void fillHole(HoledImage image) throws NotImplementedFunctionException { throw new NotImplementedFunctionException(); }
    static float calcColor(Pixel h, Set<Pixel> B, WeightFunc w) throws NotImplementedFunctionException { throw new NotImplementedFunctionException();}
}

class NotImplementedFunctionException extends Exception { }