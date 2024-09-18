/* Copyright 2018-24 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, pParse._, pgui._

object LessonsLaunch extends GuiLaunchMore
{
  override def settingStr: String = "lessons"

  override def default: (CanvasPlatform => Any, String) = (LsACircles.canv, "JavaFx" -- LsACircles.title)

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val code: String = sts.findSettingIdentifierElse("code", "A1")

    val res = theMap(code)
    (res.canv, "JavaFx" -- res.title)
  }

  val aList: RArr[LessonGraphics] = RArr(LsACircles, LsASquares, LsACircles2, LsAPolygons, LsARotation, LsAShapes2, LsAShapes, LsABeziers, LsADiagram, LsAReflect, LsAHexEnum, LsATiling, LsAArcs, LsAEllipses, LsAInner)
  val bList = RArr(LsTimer, LsB2, LsB3)
  val cList = RArr(LsC1, LsC2, LsC3, LsC3b, LsC4, LsC5, LsC6, LsC7, LsC8)
  val dList = RArr(LsD1, LsD2, LsD3, LsD4, LsD5)
  val eList = RArr(LsE1, LsE2)

  def theMap(inp: String): LessonGraphics = inp.findAlphaInt.fold(LsACircles){
    case ("A", i) => if (i > aList.length || i < 1) aList(0) else aList(i - 1)
    case ("B", i) => if (i > bList.length || i < 1) bList(0) else bList(i - 1)
    case ("C", i) => if (i > cList.length || i < 1) cList(0) else cList(i - 1)
    case ("D", i) => if (i > dList.length || i < 1) dList(0) else dList(i - 1)
    case ("E", i) => if (i > eList.length || i < 1) eList(0) else eList(i - 1)
    case _ => aList(0)
  }
}