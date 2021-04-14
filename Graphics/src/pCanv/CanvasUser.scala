/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCanv
import geom._

/** So the descendant classes need to set the canv.mouseup field to use the mouse and its equivalents. */
abstract class CanvasUser(val title: String)
{
  val canv: CanvasPlatform

  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: Arr[GraphicElem]): Arr[GraphicActive] =
  {
    val activeBuff: Buff[GraphicActive] = Buff()
    movedObjs.foreach {
      case el: GraphicActiveOld => activeBuff += el
      case _ =>
    }

    movedObjs.foreach(processGraphic)

    def processGraphic(el: GraphicElem): Unit = el match
    {
      case el: GraphicClickable => activeBuff += el
      case sc: ShapeCompound => { sc.rendToCanvas(canv); sc.children.foreach(processGraphic) }
      //case cs: GraphicParentOld => canv.rendElems(cs.children)
      case cpf: GraphicParentFull => canv.rendElems(cpf.children)
      case ce: GraphicElem => ce.rendToCanvas(canv)
      //s case nss: UnScaledShape => canv.rendElems(nss.elems.slate(nss.referenceVec))
      //case v =>
    }
    activeBuff.toReverseRefs
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}