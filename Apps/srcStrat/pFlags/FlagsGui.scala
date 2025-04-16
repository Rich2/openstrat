/* Copyright 2018-25 Richard Oliver and w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, pgui._, Colour._

/** A shortcut application to display some flags. */
case class FlagsGui(canv: CanvasPlatform) extends CanvasNoPanels("Flags Gui")
{ backColour = Gray
  var big: Flag = UnitedStates
  val bigScale:Double = 600
  val smallScale: Double = 100
  def bigRect: RectCompound = big.compoundStr.scale(bigScale).slateY(canv.top - bigScale / 2)
  val margin: Double = 10

  val flagsArr: RArr[Flag] = RArr(PapuaNewGuinea, Eritrea, India, Iraq, CCCP, CzechRepublic, Colombia, Chile, Cyprus, Armenia, Austria, Belgium,
    Chad, China, England, France, Germany, Germany1871, Italy, Ireland, Japan, Russia, USSR, UnitedKingdom, UnitedStates, WhiteFlag,
    CommonShapesInFlags)

  def flagLines: GraphicElems =
  { var y: Double = canv.top - bigScale - margin - smallScale / 2
    var x: Double = canv.left
    var rowFirst: Boolean = true
    flagsArr.map{ flag =>
      val res1: PolygonCompound = flag.compound().scale(smallScale)
      val dx = res1.boundRight
      if (!rowFirst && (x + dx * 2> canv.right))
      { y -= (smallScale + margin)
        x = canv.left
        rowFirst = true
      }
      else
      { x += margin
        rowFirst = false
      }
      val res2 = res1.slate(x + dx, y)
      x += dx * 2
      res2
    }
  }

  val tlFlags = RArr(Armenia, Austria, England, UnitedKingdom, Japan)
  val tlObjs = tlFlags.iMap((i, el) => el.compoundStr.scale(100).topLeftTo(canv.topLeft.slateYFrom(i * 110)))

  val trFlags = RArr(Belgium, Chad, France)
  val trObjs = trFlags.iMap((i, el) => el.compoundStr.scale(100).topRightTo(canv.topRight.slateYFrom(i * 110)))

  val blFlags = RArr(China, Italy, India)
  val blObjs = blFlags.iMap((i, el) => el.compoundStr.scale(100).bottomLeftTo(canv.bottomLeft.slateY(i * 110)))

  val brFlags = RArr(Germany, Germany1871, Ireland, UnitedStates)
  val brObjs = brFlags.iMap((i, el) => el.compoundStr.scale(100).bottomRightTo(canv.bottomRight.slateY(i * 110)))

  val starCen = Pt2(300, 0)
  val star: GraphicElems = RArr(Star5.fill(White), Star5.crossLines()).scale(500).slate(starCen)
  val cr = Cross().slate(starCen)

  def allStuff: RArr[Graphic2Elem] = /*tlObjs ++ trObjs ++ blObjs ++ brObjs ++ */ flagLines +% bigRect //+- myr

  mouseUp = (_, li, _) =>
  { val str: String = li.headFoldToString("No clickable object on canvas")
    val tg = TextFixed.xy(str, 28, 0, 100)
    if (li.nonEmpty) li(0) match {
      case fl : Flag => big = fl
      case _ => 
    }
    repaint(allStuff +%  tg)

  }

  repaint(allStuff)
}