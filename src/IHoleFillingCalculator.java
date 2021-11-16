import java.util.Set;

public interface IHoleFillingCalculator {
    // TODO - do we actually need NotImplementedFunctionException? to check this
    static void fillHole(HoledImage image) throws NotImplementedFunctionException { throw new NotImplementedFunctionException(); }
    static float calcColor(Pixel h, Set<Pixel> B, IWeightFunc w) throws NotImplementedFunctionException { throw new NotImplementedFunctionException();}
}

class NotImplementedFunctionException extends Exception { }