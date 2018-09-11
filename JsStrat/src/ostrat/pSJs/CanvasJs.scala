/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import geom._, pCanv._, org.scalajs.dom._

object CanvasJs extends CanvasTopLeft
{
   selfJs =>
   val can: html.Canvas = document.getElementById("scanv").asInstanceOf[html.Canvas] 
   override def width = can.width
   override def height = can.height 
   def setup() =
   {
      can.width = (window.innerWidth).max(200).toInt //-20
      can.height = (window.innerHeight).max(200).toInt //-80
   }
   setup
   
   def getButton(e: MouseEvent): MouseButton = e.button match
   {
      case 0 => LeftButton
      case 1 => MiddleButton
      case 2 => RightButton
   } 
   can.onmouseup = (e: MouseEvent) =>
      {
         val rect: ClientRect = can.getBoundingClientRect()
         mouseUpTopLeft(e.clientX - rect.left, e.clientY -rect.top, getButton(e))
      }
      
   can.onmousedown = (e: MouseEvent) =>
      {
         val rect = can.getBoundingClientRect()         
         mouseDownTopLeft(e.clientX - rect.left, e.clientY  -rect.top, getButton(e))
      }  

   can.asInstanceOf[scalajs.js.Dynamic].onwheel = (e: WheelEvent) => 
      {
         e.deltaY match
         {
            case 0 => 
            case d if d < 0 => onScroll(true)
            case _ => onScroll(false)
         }
         e.preventDefault() //Stops the page scrolling when the mouse pointer is over the canvas
      }
      
   can.oncontextmenu = (e: MouseEvent) => e.preventDefault()
   window.onresize = (e: UIEvent) =>
   {
      setup
      resize()
   }   
     
   override def getTime: Double = new scala.scalajs.js.Date().getTime()      
   override def timeOut(f: () => Unit, millis: Integer): Unit = window.setTimeout(f, millis.toDouble) 
   
   val gc = can.getContext("2d").asInstanceOf[raw.CanvasRenderingContext2D]   
   
   override def tlPolyFill(fp: PolyFill): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(fp.xHead, fp.yHead)      
      fp.verts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.fillStyle = fp.colour.str            
      gc.fill()               
   }
   override def tlPolyDraw(dp: PolyDraw): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(dp.xHead, dp.yHead)
      dp.verts.foreachPairTail(gc.lineTo)
      gc.closePath()      
      gc.strokeStyle = dp.lineColour.str
      gc.lineWidth = dp.lineWidth
      gc.stroke            
   }
   override def tlPolyFillDraw(pfd: PolyFillDraw): Unit =    
   {      
      gc.beginPath()
      gc.moveTo(pfd.xHead, pfd.yHead)
      pfd.verts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.fillStyle = pfd.fillColour.str            
      gc.fill()
      gc.strokeStyle = pfd.lineColour.str
      gc.lineWidth = pfd.lineWidth
      gc.stroke            
   }
   
   override def tlLineDraw(ld: LineDraw): Unit =
   {
      gc.beginPath
      gc.moveTo(ld.xStart, ld.yStart)
      gc.lineTo(ld.xEnd, ld.yEnd)
      gc.strokeStyle = ld.colour.str
      gc.lineWidth = ld.lineWidth
      gc.stroke()
   }
   
   override protected def tlArcDraw(ad: ArcDraw): Unit =
   {
      gc.beginPath
      gc.moveTo(ad.xStart, ad.yStart)
      ad.fControlEndRadius(gc.arcTo)
   }
   
   override protected def tlLinesDraw(lineSegs: Line2s, lineWidth: Double, linesColour: Colour): Unit =
   {           
      gc.beginPath
      lineSegs.foreach(ls => { gc.moveTo(ls.xStart, ls.yStart);  gc.lineTo(ls.xEnd, ls.yEnd)})
      gc.strokeStyle = linesColour.str
      gc.stroke()      
   }
   protected def tlBezierDraw(bd: BezierDraw): Unit =
   {
      gc.beginPath()
      gc.moveTo(bd.xStart, bd.yStart)
      gc.strokeStyle = bd.colour.str
      gc.lineWidth = bd.lineWidth
      gc.stroke()
   }
   
   private def segsPath(segs: CurveSegs): Unit =
   {
      gc.beginPath()           
      var startPt = segs.last.pEnd
      gc.moveTo(startPt.x, startPt.y)      
      segs.foreach{seg =>
         seg.segDo(ls =>  gc.lineTo(ls.xEnd, ls.yEnd),
               as => as.fControlEndRadius(startPt, gc.arcTo),
            bs => gc.bezierCurveTo(bs.xC1, bs.yC1, bs.xUses, bs.yUses, bs.xEnd, bs.yEnd))           
            startPt = seg.pEnd
         }
         gc.closePath
   }
   
   override def tlShapeFill(sf: ShapeFill): Unit =
   {
      segsPath(sf.segs)  
      gc.fillStyle = sf.fillColour.str
      gc.fill
   }   
   
   override def tlShapeDraw(segs: CurveSegs, lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)      
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke   
   }     
   override def tlShapeFillDraw(segs: CurveSegs, fillColour: Colour, lineWidth: Double, lineColour: Colour): Unit =
   {
      segsPath(segs)  
      gc.fillStyle = fillColour.str
      gc.fill
      gc.strokeStyle = lineColour.str
      gc.lineWidth = lineWidth
      gc.stroke   
   } 
   
   override def tlTextGraphic(tg: TextGraphic): Unit = 
   {      
      gc.textAlign = tg.align.jsStr
      gc.textBaseline = "middle"
      gc.font = tg.fontSize.toString + "px Arial"
      gc.fillStyle = tg.colour.str
      gc.fillText(tg.str, tg.posn.x, tg.posn.y)      
   }
   override def tlTextOutline(to: TextOutline): Unit = 
   {
      gc.strokeStyle = to.colour.str
      gc.lineWidth = to.lineWidth
      gc.textAlign = to.align.jsStr
      gc.textBaseline = "middle"
      gc.font = to.fontSize.toString + "px Arial"      
      gc.strokeText(to.str, to.posn.x, to.posn.y)
   }   

   override def clear(colour: Colour): Unit =
   {
      gc.fillStyle = colour.str
      gc.fillRect(0, 0, width, height)
   }
   override def tlClip(pts: Vec2s): Unit =
   {
      gc.beginPath     
      gc.moveTo(pts.head1, pts.head2)
      pts.foreachPairTail(gc.lineTo)
      gc.closePath()
      gc.clip()
   }
   override def gcRestore(): Unit = gc.restore() 
   override def gcSave(): Unit = gc.save()
   
   override def saveFile(fileName: String, output: String): Unit = window.localStorage.setItem(fileName, output)     
   override def loadFile(fileName: String): EMon[String] =
   {
      val nStr = window.localStorage.getItem(fileName)
      if (nStr == null) bad1(FilePosn(1, 1, "Js Error"), "File not found") else Good(nStr)
   }       
}
