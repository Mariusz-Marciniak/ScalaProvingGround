package pl.mariusz.marciniak.di.st

  trait DataProvider {
    def nextArgument: Int  
  }
  
  class FakeDbDataProvider extends DataProvider {
    val data = (1 to 5).iterator
    def nextArgument: Int = if(data.hasNext) data.next else 0
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
  
  //class Calculator(dataProvider: { def nextArgument: Int }, operation: { def exec(a: Int, b: Int): Int}) {
  class Calculator(data: { 
    def operation: Operation 
    def dataProvider: DataProvider
    }) {  
    //def calculate(): Int = data.exec(data.nextArgument, data.nextArgument)
    def calculate(): Int = data.operation.exec(data.dataProvider.nextArgument, data.dataProvider.nextArgument)
  }

  object AdditionFromConstantConfig {
    def operation: Operation = new Addition 
    def dataProvider: DataProvider = new ConstantDataProvider
  }
  
  object Main {
      def main(args: Array[String]) {
        //val operation = new Multiplication()
        println("Structural Typing DI")
        val calc = new Calculator(AdditionFromConstantConfig)
        println("first operation result: "+calc.calculate)
        println("second operation result: "+calc.calculate)
    }
  }