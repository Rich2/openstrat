/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, pCanv._, Colour._

/** A shortcut application to display some flags. */
case class FlagsGui(canv: CanvasPlatform) extends CanvasNoPanels("Flags Gui")
{
  backColour = Gray
   
  val tlFlags = Arr(Armenia, Austria, England, UnitedKingdom, Japan)
  val tlObjs = tlFlags.iMap((el, i) => el.compoundStr.scale(100).topLeftSlate(canv.topLeft.subY(i * 110)))

  val trFlags = Arr(Belgium, Chad, France)
  val trObjs = trFlags.iMap((el, i) => el.parentStrOld.scale(100).copyBoundingTR.slate(canv.topRight.subY(i * 110)))

  val blFlags = Arr(China, Italy)
  val blObjs = blFlags.iMap((el, i) => el.parentStrOld.scale(100).copyBoundingBL.slate(canv.bottomLeft.addY(i * 110)))

  val brFlags = Arr(Germany, Germany1871, Ireland)
  val brObjs = brFlags.iMap((el, i) => el.parentStrOld.scale(100).copyBoundingBR.slate(canv.bottomRight.addY(i * 110)))

  //val cenFlags = Refs(UnitedStates, Russia, USSR)
  //val cenObjs = cenFlags.iMap((el, i) => el.subj.scale(100).slateY(200 - i * 110))
  //val myr = Rectangle(2).fillTextActive(Violet, "Hello", "This Text").scale(400).slateY(300)

  val starCen = 300 vv 0
  val star: GraphicElems = Arr(Star5.fill(White), Star5.crossLines()).scale(500).slate(starCen)
  val cr = Cross().slate(starCen)
  val ind = India.parentStrOld.scale(800)//.slate(-300, 350)
  //val us2 = UnitedStates.inset.scale(500)
  val notChanging: Arr[GraphicElem] = tlObjs ++ trObjs ++ blObjs ++ brObjs +- ind //+- myr

  mouseUp = (_, li, _) =>
  { val str: String = li.headToStringElse("No clickable object on canvas")
    val tg = TextGraphic(str, 28, 0 vv 100)
    repaint(notChanging +-  tg)
  }

  repaint(notChanging)
}