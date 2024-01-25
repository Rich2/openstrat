/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, pParse._, pgui._

object Lessons extends GuiLaunch
{
  override def settingStr: String = "lessons"

  override def default: (CanvasPlatform => Any, String) = (LsACircles1.canv, "JavaFx" -- LsACircles1.title)

  override def apply(expr: AssignMemExpr): (CanvasPlatform => Any, String) =
  {
    val obj = expr match
    {
      case it: IdentifierToken => theMap(it.srcStr)
      case _ => theMap("")
    }
    (obj.canv, "JavaFx" -- obj.title)
  }

  def theMap(inp: String): GraphicsA = inp match
  {
    case "A1" => LsACircles1
    case "A2" => LsACircles2
    case "A3" => LsASquares
    case "A4" => LsPolygons
//    case "A5" => (LsA3a(_), "JavaFx Demonstration Canvas 3a")
//    case "A6" => (LsA3(_), "JavaFx Demonstration Canvas 3")
//    case "A7" => (LsA4(_), "JavaFx Demonstration Canvas 4")
//    case "A8" => (LsA5(_), "JavaFx Demonstration Canvas 5")
//    case "A9" => (LsAText(_), "JavaFx Demonstration Canvas 6")
    case "A7" => LsAShapes2
    case "A8" => LsAShapes
    case "A9" => LsABeziers
    case "A10" => LsADiagram
    case "A11" => LsAReflect
    case "A12" => LsAHexEnum
//    case "A16" => (LsA13(_), "Floor tiling Squares with diags")
    case "A18" => LsA18
    case "A19" => LsAEllipses

//
//    case "B1" => (learn.LsB1(_), "JavaFx Demonstration Animated Canvas 1") //Moving Graphics
//    case "B2" => (learn.LsB2(_), "JavaFx Demonstration Animated Canvas 2")
//    case "B3" => (learn.LsB3(_), "JavaFx Demonstration Animated Canvas 3")
//
//    case "C1" => (learn.LsC1(_), "JavaFx Demonstration Interactive Canvas 1") //User interactive graphics
//    case "C2" => (learn.LsC2(_), "JavaFx Demonstration Interactive Canvas 2")
//    case "C3" => (learn.LsC3(_), "JavaFx Demonstration Interactive Canvas 3")
//    case "C3b" => (learn.LsC3b(_), "JavaFx Demonstration Interactive Canvas 3b")
//    case "C4" => (learn.LsC4(_), "JavaFx Demonstration Interactive Canvas 4")
//    case "C5" => (learn.LsC5(_), "JavaFx Demonstration Interactive Canvas 5")
//    case "C6" => (learn.LsC6(_), "JavaFx Demonstration Interactive Canvas 6")
//    case "C7" => (learn.LsC7(_), "JavaFx Demonstration Interactive Canvas 7: Exploring Beziers")
//    case "C8" => (learn.LsC8(_), "JavaFx Demonstration Interactive Canvas 8: More Dragging")
//
//    case "D1" => (learn.LsD1(_), "JavaFx Demonstration Persistence 1") //Persistence, saving and retrieving data outside of code
//    case "D2" => (learn.LsD2(_), "JavaFx Demonstration Persistence 2")
//    case "D3" => (learn.LsD3(_), "JavaFx Demonstration Persistence 3")
//    case "D4" => (learn.LsD4(_), "JavaFx Demonstration Persistence 4")
//    case "D5" => (learn.LsD5(_), "JavaFx Demonstration Persistence 5")
//
//    case "E1" => (learn.LsE1(_), "JavaFx Demonstration Games 1") //Building turn based games.
//    case "E2" => (learn.LsE2(_), "JavaFx Demonstration Games 2")

    case _ => LsACircles1
  }
}