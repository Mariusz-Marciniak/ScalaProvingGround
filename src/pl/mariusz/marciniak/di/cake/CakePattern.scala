package pl.mariusz.marciniak.di.cake {

  trait DataProviders {
    val dataProvider: DataProvider
    trait DataProvider {
      def nextArgument: Int
    }

    class FakeDbDataProvider extends DataProvider {
      val data = (1 to 5).iterator
      def nextArgument: Int = if (data.hasNext) data.next else 0
    }

    class ConstantDataProvider extends DataProvider {
      val nextArgument: Int = 1
    }
  }
  
  trait Operations {
    val operation: Operation
    trait Operation {
      def exec(a: Int, b: Int): Int
    }

    class Addition extends Operation {
      def exec(a: Int, b: Int): Int = a + b
    }

    class Multiplication extends Operation {
      def exec(a: Int, b: Int): Int = a * b
    }
  }

  trait CalculatorComponent { this: DataProviders with Operations =>
    val calculator = new Calculator
    class Calculator {
      def calculate(): Int = operation.exec(dataProvider.nextArgument, dataProvider.nextArgument)
    }
  }

  object AdditionFromFakeDbDataConfig extends CalculatorComponent with Operations with DataProviders {
      val operation = new Addition
      val dataProvider = new FakeDbDataProvider
  }
  
  object Main {
    def main(args: Array[String]) {
      println("Cake Pattern DI")
      println("first operation result: " + AdditionFromFakeDbDataConfig.calculator.calculate)
      println("second operation result: " + AdditionFromFakeDbDataConfig.calculator.calculate)
    }
  }
}