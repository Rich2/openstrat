/* Copyright 2018-25 Licensed under Apache Licence version 2.0. */
package learn
import ostrat.*, pParse.*, pgui.*

object LessonsLaunch extends GuiLaunchMore
{ override def settingStr: String = "lessons"

  override def default: (CanvasPlatform => Any, String) = (LsCircles1.canv, "JavaFx" -- LsCircles1.title)

  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) =
  { val code: String = sts.findSettingIdElse("code", "A1")
    val (alpha, codeNum) = code.alphaNatPartition
    var res = allList.head
    val (str0, c0) = res.title.alphaNatPartition
    var lessonNum = c0
    var matchNum = alpha.countMatchingChars(str0)
    allList.tailForeach{ newLesson =>
      val (str, newLessonNum) = newLesson.title.alphaNatPartition
      val newMatchNum = alpha.countMatchingChars(str)
      val good: Boolean = newMatchNum match
      { case nm if nm > matchNum => true
        case nm if nm == matchNum => (newLessonNum - codeNum).abs < (lessonNum - codeNum).abs
        case _ => false
      }
      if (good)
      { res = newLesson
        matchNum = newMatchNum
        lessonNum = newLessonNum
      }
    }
    (res.canv, "JavaFx" -- res.title)
  }

  val aList: RArr[LessonGraphics] = RArr(LsCircles1, LsASquares, LsCircles2, LsPolygons, LsARotation, LsArcs1, LsArcs2, LsAShapes, LsAShapesReproduction,
    LsBeziers, LsDiagram, LsAReflect, LsAHexEnum, LsATiling, LsAEllipses, LsInnerRect, LsAPolygonSides, LsAText)

  val bList: RArr[LessonGraphics] = RArr(LsTimer, LsMovingRectangle, LsMovingRectangles2)

  val cList: RArr[LessonGraphics] = RArr(LsMouseClick, LsPointerPosition, LsPointerTargeting, LsEllipseTargeting, LsEllipseTargeting2, LsOverlapTargeting,
    LsKeyUp, LsBezierChange, LsPointerMoving2)

  val dList: RArr[LessonGraphics] = RArr(LsRson1, LsRson2, LsD3, LsD4, LsD5)
  val eList: RArr[LessonGraphics] = RArr(LsE1, LsE2)

  val allList = aList ++ bList ++ cList ++ dList ++ eList

  def theMap(inp: String): LessonGraphics = inp.findAlphaInt.fold(LsCircles1){
    case ("A", i) => if (i > aList.length || i < 1) aList(0) else aList(i - 1)
    case ("B", i) => if (i > bList.length || i < 1) bList(0) else bList(i - 1)
    case ("C", i) => if (i > cList.length || i < 1) cList(0) else cList(i - 1)
    case ("D", i) => if (i > dList.length || i < 1) dList(0) else dList(i - 1)
    case ("E", i) => if (i > eList.length || i < 1) eList(0) else eList(i - 1)
    case _ => aList(0)
  }
  
  val rsonText: String =
  { val als = aList.iMap{ (i, l) => "A" + (i + 1).toString -- aList(i).title }.mkStr("\n")
    val bls = bList.iMap{ (i, l) => "B" + (i + 1).toString -- bList(i).title }.mkStr("\n")
    val cls = cList.iMap{ (i, l) => "C" + (i + 1).toString -- cList(i).title }.mkStr("\n")
    val dls = dList.iMap{ (i, l) => "D" + (i + 1).toString -- dList(i).title }.mkStr("\n")
    val els = eList.iMap{ (i, l) => "E" + (i + 1).toString -- eList(i).title }.mkStr("\n")
    val top = """/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */"""
    top + "\n\ncode = A1" --- """/*""" --- als --- bls --- cls --- dls --- els --- """*/"""
  }
}