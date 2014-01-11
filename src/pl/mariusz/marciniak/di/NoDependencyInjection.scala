package pl.mariusz.marciniak.di {

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

  trait Operation {
    def exec(a: Int, b: Int): Int
  }

  class Addition extends Operation {
    def exec(a: Int, b: Int): Int = a + b
  }

  class Multiplication extends Operation {
    def exec(a: Int, b: Int): Int = a * b
  }

  class Calculator {
    val dataProvider = new FakeDbDataProvider()
    val operation = new Addition()

    def calculate(): Int = operation.exec(dataProvider.nextArgument, dataProvider.nextArgument)
  }

  object Main {
    def main(args: Array[String]) {
      val calc = new Calculator
      println("Tight Coupling DI")
      println("first operation result: " + calc.calculate)
      println("second operation result: " + calc.calculate)
    }
  }

}