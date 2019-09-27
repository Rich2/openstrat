/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pCanv
import geom._

abstract class CanvasUser(val title: String)
{
  val canv: CanvasPlatform
  /** This reverses the order of the GraphicActive List. Method paints objects to screen as side effect. */
  def paintObjs(movedObjs: Arr[GraphicElem]): Arr[GraphicActive] =
  {
    val subjBuff: Buff[GraphicActive] = Buff()
      
    movedObjs.foreach
    { case ce: PaintElem => ce.rendElem(canv)

      case cs: GraphicSubject =>
      { canv.rendElems(cs.elems)
        subjBuff += cs
      }

      case nss: NoScaleShape =>
      { canv.rendElems(nss.elems.slate(nss.referenceVec))
        subjBuff += nss
      }

       case ga: GraphicActive => subjBuff += ga
    }
    subjBuff.toArr.reverse
  }
   
  def refresh(): Unit   
  canv.resize = () => refresh()   
}