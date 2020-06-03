/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

/** So the descendant classes need to set the canv.mouseup field to use the mouse and its equivalents. */
abstract class CanvasUser(val title: String)
{
  val canv: CanvasPlatform

  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: Arr[DisplayElem]): Arr[DisplayActive] =
  { val activeBuff: Buff[DisplayActive] = Buff()
    movedObjs.foreach {
      case el: DisplayActive => activeBuff += el
      case _ =>
    }

    movedObjs.foreach
    { 
      case ce: GraphicElem => ce.rendToCanvas(canv)
      case cs: DisplayParent => canv.rendElems(cs.children)
      case cpf: DisplayParentFull => canv.rendElems(cpf.children)
      //s case nss: UnScaledShape => canv.rendElems(nss.elems.slate(nss.referenceVec))
      case v =>
    }
    activeBuff.toReverseRefs
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}