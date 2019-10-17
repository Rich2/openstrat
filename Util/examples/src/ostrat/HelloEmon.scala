package ostrat

object HelloEmon extends App
{ println("Welcome to Hello Emon. This will printout the first number but not the second.")
  "4".findInt.foreach(i => println(i.str))


  val d1: EMon[Int] = "2.2".findInt
  d1.foreach(i => println(i.str))// nothing happens.
  val i1: Int = d1.getElse(0)
  println("You asked for " + i1.str)
  val s1: String = d1.fold(_ => "This is not an Int", "This really is an Int: " + _)
  println(s1)
}
