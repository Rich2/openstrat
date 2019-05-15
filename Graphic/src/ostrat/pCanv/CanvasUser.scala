/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

abstract class CanvasUser(val title: String)
{
  val canv: CanvasPlatform
  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: Seq[GraphicElem]): List[GraphicActive] =
  {
    var subjs: List[GraphicActive] = Nil
      
    movedObjs.foreach
    { case ce: PaintElem => canv.rendElem(ce)         
      case cs: GraphicSubject => { canv.rendElems(cs.elems); subjs ::= cs }         
      case nss: NoScaleShape =>
        { canv.rendElems(nss.elems.slate(nss.referenceVec));
        subjs ::= nss
        }
      case ga: GraphicActive => subjs ::= ga  
    }
    subjs
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}