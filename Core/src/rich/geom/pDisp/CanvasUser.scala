/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package rich
package geom
package pDisp

trait CanvUser
{   
   val canv: CanvasLike
   def paintObjs(movedObjs: Seq[CanvObj[_]], pan: PanelLike) = movedObjs.foreach(_ match
      {
         case ce: ClickEl[_] => pan.subjsAdd(ce.ptIn, ce.retObj)
         case ce: CanvEl[_] => canv.rendElem(ce)
         case cs: CanvSubj[_] =>
         {
            cs.elems.foreach(_ match
                  {
               case FillTextRel(str, fontSize, fontColour, posn, align) => canv.rendElem(FillText(cs.cen + posn, str, fontSize, fontColour, align))
               case el => canv.rendElem(el)
                  })
            canv.rendElems(cs.elems)
            pan.subjsAdd(cs.ptIn, cs.evObj)
         }
         case FixedShape(posn, shape, evObj, elems) =>
         {
            canv.rendElems(elems.slate(posn))
            pan.subjsAdd(shape.slate(posn).ptInShape, evObj)
         }
      })
    def refresh(): Unit
//    def fCanvType[A](fBasic: CanvasLike => A, fSaver: CanvasSaver => A): A = canv match
//    {
//       case cs: CanvasSaver => fSaver(cs)
//       case cd => fBasic(cd)
//    }
//   def CanvasSaverSeq[A](f: CanvasSaver => Seq[A]): Seq[A] =  canv match
//   {
//      case cs: CanvasSaver => f(cs)
//      case cd => Seq()
//   }
//   def CanvasSaverDo(f: CanvasSaver => Unit): Unit = fCanvType[Unit](_ => {}, f)
   canv.resize = () => refresh()
}