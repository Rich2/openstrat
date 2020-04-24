/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** So the descendant classes need to set the canv.mouseup field to use the mouse and its equivalents. */
abstract class CanvasUser(val title: String)
{
  val canv: CanvasPlatform

  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: Arr[GraphicElem]): Arr[GraphicActive] =
  { val activeBuff: Buff[GraphicActive] = Buff()
    movedObjs.foreach {
      case el: GraphicActive => activeBuff += el
      case _ =>
    }

    movedObjs.foreach
    { case ce: PaintElem => ce.rendToCanvas(canv)
      case cs: GraphicParent => canv.rendElems(cs.children)
     //s case nss: UnScaledShape => canv.rendElems(nss.elems.slate(nss.referenceVec))
      case v =>
    }
    activeBuff.toReverseRefs
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}