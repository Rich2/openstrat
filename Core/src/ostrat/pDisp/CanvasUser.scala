/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDisp
import geom._

trait CanvUser
{   
   val canv: CanvasLike
   def paintObjs(movedObjs: Seq[CanvObj[_]], pan: PanelLike) = movedObjs.foreach(_ match
      {
         //case ce: ClickEl[_] => pan.subjsAdd(ce.clickObj)
         case ce: CanvEl[_] => canv.rendElem(ce)
         case cs: CanvSubj[_] =>
         {
            cs.elems.foreach(_ match
                  {
               case FillTextRel(str, fontSize, fontColour, posn, align) => canv.rendElem(FillText(cs.cen + posn, str, fontSize, fontColour, align))
               case el => canv.rendElem(el)
                  })
            canv.rendElems(cs.elems)
            pan.subjs :+= cs
         }
         case fs: FixedShape => 
         {
            canv.rendElems(fs.elems.slate(fs.posn))
            pan.subjs :+= fs
         }
      })
    def refresh(): Unit

   canv.resize = () => refresh()
}