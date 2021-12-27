/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, Colour._, pParse._, pgui._

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

object Lessons extends GuiLaunch
{
  override def settingStr: String = "lessons"

  override def default: (CanvasPlatform => Any, String) = (LsA1a(_), "JavaFx Demonstration Canvas A1")
  override def apply(expr: AssignmentMemExpr): (CanvasPlatform => Any, String) = expr match {
    case it: IdentifierToken => theMap(it.srcStr)
    case _ => theMap("")
  }

  def theMap(inp: String): (CanvasPlatform => Any, String) = inp match {
    case "A1a" => (LsA1a(_), "JavaFx Demonstration Canvas A1")
    case "A1b" => (LsA1b(_), "JavaFx Demonstration Canvas 1b")
    case "A1" => (LsA1(_), "JavaFx Demonstration Canvas A1 Summary")
    case "A2" => (LsA2(_), "JavaFx Demonstration Canvas 2")
    case "A3a" => (LsA3a(_), "JavaFx Demonstration Canvas 3a")
    case "A3" => (LsA3(_), "JavaFx Demonstration Canvas 3")
    case "A4" => (LsA4(_), "JavaFx Demonstration Canvas 4")
    case "A5" => (LsA5(_), "JavaFx Demonstration Canvas 5")
    case "A6" => (LsA6(_), "JavaFx Demonstration Canvas 6")
    case "A7" => (LsA7(_), "JavaFx Demonstration Canvas 7")
    case "A8" => (LsA8(_), "JavaFx Demonstration Canvas 8")
    case "A9" => (LsA9(_), "JavaFx Demonstration Canvas 9")
    case "A10" => (LsA10(_), "JavaFx Demonstration Canvas 10")
    case "A11" => (LsA11(_), "Reflecting a point across a Line")
    case "A12" => (LsA12(_), "Hexagons")

    case "B1" => (learn.LsB1(_), "JavaFx Demonstration Animated Canvas 1") //Moving Graphics
    case "B2" => (learn.LsB2(_), "JavaFx Demonstration Animated Canvas 2")
    case "B3" => (learn.LsB3(_), "JavaFx Demonstration Animated Canvas 3")

    case "C1" => (learn.LsC1(_), "JavaFx Demonstration Interactive Canvas 1") //User interactive graphics
    case "C2" => (learn.LsC2(_), "JavaFx Demonstration Interactive Canvas 2")
    case "C3" => (learn.LsC3(_), "JavaFx Demonstration Interactive Canvas 3")
    case "C4" => (learn.LsC4(_), "JavaFx Demonstration Interactive Canvas 4")
    case "C5" => (learn.LsC5(_), "JavaFx Demonstration Interactive Canvas 5")
    case "C6" => (learn.LsC6(_), "JavaFx Demonstration Interactive Canvas 6")
    case "C7" => (learn.LsC7(_), "JavaFx Demonstration Interactive Canvas 7: Exploring Beziers")
    case "C8" => (learn.LsC8(_), "JavaFx Demonstration Interactive Canvas 8: More Dragging")

    case "D1" => (learn.LsD1(_), "JavaFx Demonstration Persistence 1") //Persistence, saving and retrieving data outside of code
    case "D2" => (learn.LsD2(_), "JavaFx Demonstration Persistence 2")
    case "D3" => (learn.LsD3(_), "JavaFx Demonstration Persistence 3")
    case "D4" => (learn.LsD4(_), "JavaFx Demonstration Persistence 4")
    case "D5" => (learn.LsD5(_), "JavaFx Demonstration Persistence 5")

    case "E1" => (learn.LsE1(_), "JavaFx Demonstration Games 1") //Building turn based games.
    case "E2" => (learn.LsE2(_), "JavaFx Demonstration Games 2")

    case _ => (LsA1(_), "JavaFx Demonstration Canvas A1 Summary")
  }
}