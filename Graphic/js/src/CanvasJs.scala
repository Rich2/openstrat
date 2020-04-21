/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pSJs
import geom._, pCanv._, org.scalajs.dom._

object CanvasJs extends CanvasTopLeft
{ val can: html.Canvas = document.getElementById("scanv").asInstanceOf[html.Canvas]
  override def width = can.width
  override def height = can.height
  def setup() =
  { can.width = (window.innerWidth).max(200).toInt //-20
    can.height = (window.innerHeight).max(200).toInt //-80
    //tabindex required for canvas to recieve key.Events (now focusable)
    can.setAttribute("tabindex","1")
    //give focus to listen for key.Events
    can.focus()
  }
  setup
   
  def getButton(e: MouseEvent): MouseButton = e.button match
  { case 0 => LeftButton
    case 1 => MiddleButton
    case 2 => RightButton
  }

  can.onmouseup = (e: MouseEvent) =>
  { val rect: ClientRect = can.getBoundingClientRect()
    mouseUpTopLeft(e.clientX - rect.left, e.clientY -rect.top, getButton(e))
  }

  can.onmousedown = (e: MouseEvent) =>
  { val rect = can.getBoundingClientRect()
    mouseDownTopLeft(e.clientX - rect.left, e.clientY -rect.top, getButton(e))
  }
  
  can.onkeyup = (e: raw.KeyboardEvent) => { keyUp(e.key) }
  can.onkeydown = (e: raw.KeyboardEvent) => { keyDown(e.key) }

  can.asInstanceOf[scalajs.js.Dynamic].onwheel = (e: WheelEvent) =>
  { e.deltaY match
    { case 0 =>
      case d if d < 0 => onScroll(true)
      case _ => onScroll(false)
    }
    e.preventDefault() //Stops the page scrolling when the mouse pointer is over the canvas
  }
      
  can.oncontextmenu = (e: MouseEvent) => e.preventDefault()
  window.onresize = (e: UIEvent) => { setup; resize() }
     
  override def getTime: Long = new scala.scalajs.js.Date().getTime().toLong
  override def timeOut(f: () => Unit, millis: Integer): Unit = window.setTimeout(f, millis.toDouble)
   
  val gc = can.getContext("2d").asInstanceOf[raw.CanvasRenderingContext2D]
   
  override protected[this] def tlPolyFill(poly: Polygon, colour: Colour): Unit =
  { gc.beginPath()
    gc.moveTo(poly(0).x, poly(0).y)
    poly.foreachPairTail(gc.lineTo)
    gc.closePath()
    gc.fillStyle = colour.webStr
    gc.fill()
  }

  override def tlPolyDraw(poly: Polygon, lineWidth: Double, colour: Colour): Unit =
  { gc.beginPath()
    gc.moveTo(poly(0).x, poly(0).y)
    poly.foreachPairTail(gc.lineTo)
    gc.closePath()
    gc.strokeStyle = colour.webStr
    gc.lineWidth = lineWidth
    gc.stroke
  }

  override protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit =
  { gc.beginPath
    gc.moveTo(pod.xStart, pod.yStart)
    pod.foreachEnd(gc.moveTo)
    gc.strokeStyle = pod.colour.webStr
    gc.lineWidth = pod.lineWidth
    gc.stroke    
  }

  override protected[this] def tlLineDraw(ld: LineDraw): Unit =
  { gc.beginPath
    gc.moveTo(ld.xStart, ld.yStart)
    gc.lineTo(ld.xEnd, ld.yEnd)
    gc.strokeStyle = ld.colour.webStr
    gc.lineWidth = ld.width
    gc.stroke()
  }
  
  override protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit =
  { gc.beginPath
    gc.moveTo(dld.xStart, dld.yStart)
    gc.lineTo(dld.xEnd, dld.yEnd)
    gc.strokeStyle = dld.colour.webStr
    gc.lineWidth = dld.lineWidth
    gc.setLineDash(scalajs.js.Array.apply(dld.dashArr: _ *))    
    gc.stroke()
    gc.setLineDash(scalajs.js.Array.apply())
  }
   
  override protected[this] def tlCArcDrawOld(ad: CArcDrawOld): Unit =
  { gc.beginPath
    gc.moveTo(ad.xStart, ad.yStart)
    ad.fControlEndRadius(gc.arcTo)
    gc.strokeStyle = ad.colour.webStr
    gc.stroke()
  }

  override protected[this] def tlCArcDraw(tld: CArcDraw): Unit = ???
   
  override protected[this] def tlLinesDraw(lsd: LinesDraw): Unit =
  { gc.beginPath
    lsd.lines.foreach(ls => { gc.moveTo(ls.xStart, ls.yStart);  gc.lineTo(ls.xEnd, ls.yEnd)})
    gc.lineWidth = lsd.lineWidth
    gc.strokeStyle = lsd.colour.webStr
    gc.stroke()
  }

  override protected[this] def tlBezierDraw(bd: BezierDraw): Unit =
  { gc.beginPath()
    gc.moveTo(bd.xStart, bd.yStart)
    gc.strokeStyle = bd.colour.webStr
    gc.lineWidth = bd.lineWidth
    gc.bezierCurveTo(bd.xC1, bd.yC1, bd.xC2, bd.yC2, bd.xEnd, bd.yEnd)
    gc.stroke()
  }
   
  private[this] def segsPath(segs: PolyCurve): Unit =
  { gc.beginPath()
    var startPt = segs.last.pEnd
    gc.moveTo(startPt.x, startPt.y)
    segs.foreach{seg =>
      seg.segDo(ls =>
        gc.lineTo(ls.xEnd, ls.yEnd),
        as => as.fControlEndRadius(startPt, gc.arcTo),
        bs => gc.bezierCurveTo(bs.xC1, bs.yC1, bs.xUses, bs.yUses, bs.xEnd, bs.yEnd)
      )
      startPt = seg.pEnd
    }
    gc.closePath
  }
   
  override protected[this] def tlShapeFill(shape: PolyCurve, colour: Colour): Unit =
  { segsPath(shape)
    gc.fillStyle = colour.webStr
    gc.fill
  }
   
  override protected[this] def tlShapeDraw(shape: PolyCurve, lineWidth: Double, colour: Colour): Unit =
  { segsPath(shape)
    gc.strokeStyle = colour.webStr
    gc.lineWidth = lineWidth
    gc.stroke
  }
   
  override protected[this] def tlTextGraphic(tg: TextGraphic): Unit =
  { gc.textAlign = tg.align.jsStr
    gc.textBaseline = tg.baseLine.jsStr
    gc.font = tg.fontSize.toString + "px Arial"
    gc.fillStyle = tg.colour.webStr
    gc.fillText(tg.str, tg.posn.x, tg.posn.y)
  }

  override protected[this] def tlTextOutline(to: TextOutline): Unit =
  { gc.strokeStyle = to.colour.webStr
    gc.lineWidth = to.lineWidth
    gc.textAlign = to.align.jsStr
    gc.textBaseline = to.baseLine.jsStr
    gc.font = to.fontSize.toString + "px Arial"
    gc.strokeText(to.str, to.posn.x, to.posn.y)
  }

  override def clear(colour: Colour): Unit = { gc.fillStyle = colour.webStr; gc.fillRect(0, 0, width, height) }

  override protected[this] def tlClip(pts: Polygon): Unit =
  { gc.beginPath
    gc.moveTo(pts.head1, pts.head2)
    pts.foreachPairTail(gc.lineTo)
    gc.closePath()
    gc.clip()
  }

  /** Restore GraphicContext usually used for the Clip Frame. Nothing to do with file loading. */
  override def gcRestore(): Unit = gc.restore()

  /** Save GraphicContext state usually used for the Clip Frame. Nothing to do with file saving. */
  override def gcSave(): Unit = gc.save()

  override def saveFile(fileName: String, output: String): Unit = window.localStorage.setItem(fileName, output)
  override def loadFile(fileName: String): EMon[String] =
  { val nStr = window.localStorage.getItem(fileName)
    if (nStr == null) TextPosn.empty.bad("Js Error, File not found") else Good(nStr)
  }
}
