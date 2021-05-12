/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._, pCanv._

object LsA1sApp extends App
{
  val cfs = Arr(Circle(100, 0, 0).fill(SeaGreen),
  Circle(70, 50, 80).fill(Orange),
  Circle(80, 300, 0).fill(Red),
  Circle(80, -250, 150).fill(Colour.LemonLime),
  Circle(40, 0, -220).fill(Colour.DarkGoldenRod)
  )
  println(cfs.svgList)
}

object LessonsA extends GuiLaunch
{
  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 if s3 == "A" => (LsA1a(_), "JavaFx Demonstration Canvas A1")
    case 1 if s3 == "B" => (LsA1b(_), "JavaFx Demonstration Canvas 1b")
    case 1 => (LsA1(_), "JavaFx Demonstration Canvas A1 Summary")
    case 2 => (LsA2(_), "JavaFx Demonstration Canvas 2")
    case 3 if s3 == "A" => (LsA3a(_), "JavaFx Demonstration Canvas 3a")
    case 3 => (LsA3(_), "JavaFx Demonstration Canvas 3")
    case 4 => (LsA4(_), "JavaFx Demonstration Canvas 4")
    case 5 => (LsA5(_), "JavaFx Demonstration Canvas 5")
    case 6 => (LsA6(_), "JavaFx Demonstration Canvas 6")
    case 7 => (LsA7(_), "JavaFx Demonstration Canvas 7")
    case 8 => (LsA8(_), "JavaFx Demonstration Canvas 8")
    case 9 => (LsA9(_), "JavaFx Demonstration Canvas 9")
    case 10 => (LsA10(_), "JavaFx Demonstration Canvas 10")
    case 11 => (LsA11(_), "Reflecting a point across a Line")
    case 12 => (LsA12(_), "Hexagons")
  }
}