/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFx
import geom._, pgui._, javafx._, scene._

/** A JavaFx implementation of [[CanvasPlatform]]. <a href="https://openjfx.io/index.html">JavaFx 15 documentation</a><br>
 * <a href="https://openjfx.io/javadoc/15/javafx.graphics/javafx/scene/canvas/GraphicsContext.html">GraphicContext</a>*/
case class CanvasFx(canvFx: canvas.Canvas, theScene: Scene) extends CanvasTopLeft// with CanvSaver
{
  val gc: canvas.GraphicsContext = canvFx.getGraphicsContext2D
  override def width = canvFx.getWidth.max(100)
  override def height = canvFx.getHeight.max(100)
  
  def getButton(e: input.MouseEvent): MouseButton = 
  { 
    import input.MouseButton._
    e.getButton match
    { case PRIMARY => LeftButton
      case MIDDLE => MiddleButton
      case SECONDARY => RightButton
      case NONE => NoButton
      // case BACK => BackButton
      // case FORWARD => ForwardButton
      case _ => UnknownButton
    }     
  }
  
  canvFx.setOnMouseReleased((e: input.MouseEvent) => mouseUpTopLeft(e.getX(), e.getY(), getButton(e)))   
  canvFx.setOnMousePressed((e: input.MouseEvent) => mouseDownTopLeft(e.getX(), e.getY(), getButton(e)))
  canvFx.setOnMouseMoved((e: input.MouseEvent) => mouseMovedTopLeft(e.getX(), e.getY(), getButton(e))) 
  canvFx.setOnMouseDragged((e: input.MouseEvent) => mouseDraggedTopLeft(e.getX(), e.getY(), getButton(e))) 
  
  theScene.setOnKeyReleased{(e: input.KeyEvent) => keyUp(e.getText) }
  theScene.setOnKeyPressed{(e: input.KeyEvent) => keyDown(e.getText) }

  canvFx.setOnScroll((e: input.ScrollEvent) => e.getDeltaY match
  { case 0 => //("scroll delta 0")
    case d if d > 0 => onScroll(true)
    case _ => onScroll(false)
  })

  import paint.Color
  def toFxColor(colour: Colour): Color = Color.rgb(colour.red, colour.green, colour.blue, colour.alpha / 255.0)

  def setFill(fill: FillFacet): Unit = fill match {
    case c: Colour => gc.setFill(toFxColor(c))
    case fr: FillRadial =>
    {
      import paint._
      val stop1 = new Stop(0, toFxColor(fr.cenColour))
      val stop2 = new Stop(1, toFxColor(fr.outerColour))
      val lg1 = new RadialGradient(0, 0, 0.5, 0.5, 0.8, true, CycleMethod.NO_CYCLE, stop1, stop2)
      gc.setFill(lg1)
    }
  }

  override def tlPolyFill(pf: PolygonFill): Unit =
  { setFill(pf.fill)
    gc.fillPolygon(pf.xVertsArray, pf.yVertsArray, pf.vertsNum)
  }

  /** Needs mod */
  override protected[this] def tlPolyDraw(pd: PolygonDraw): Unit =
  { //gc.beginPath
    gc.setStroke(toFxColor(pd.lineColour))
    gc.setLineWidth(pd.lineWidth)
    gc.strokePolygon(pd.xVertsArray, pd.yVertsArray, pd.vertsNum)
  }

  override protected[this] def tlLinePathDraw(pod: LinePathDraw): Unit =
  { gc.beginPath
    gc.moveTo(pod.xStart, pod.yStart)
    pod.foreachEnd(gc.lineTo)
    gc.setStroke(toFxColor(pod.colour))
    gc.setLineWidth(pod.lineWidth)
    gc.stroke()
  }
   
  override protected[this] def tlLineDraw(ld: LineSegDraw): Unit =
  { gc.beginPath
    gc.moveTo(ld.xStart, ld.yStart)
    gc.lineTo(ld.xEnd, ld.yEnd)
    gc.setStroke(toFxColor(ld.colour))
    gc.setLineWidth(ld.width)
    gc.stroke()
  }

  /** The javaFx documentation. */
  override protected[this] def tlCArcDraw(cad: CArcDraw): Unit =
  { gc.beginPath
    gc.moveTo(cad.xStart, cad.yStart)
    gc.arc(cad.xCen, cad.yCen, cad.radius, cad.radius, cad.curveSeg.startDegsYDown, cad.curveSeg.angleDeltaYDown.degs)
    gc.setStroke(toFxColor(cad.colour))
    gc.stroke()
  }

  /** The javaFx documentation. */
  override protected[this] def tlEArcDraw(ead: EArcDraw): Unit =
  { gc.beginPath
    gc.moveTo(ead.xStart, ead.yStart)
    debvar(ead.curveSeg.startDegsYDown)
    debvar(ead.curveSeg.angleDeltaYDown.degs)
    gc.arc(ead.xCen, ead.yCen, ead.curveSeg.radius1, ead.curveSeg.radius2, ead.curveSeg.startDegsYDown, ead.curveSeg.angleDeltaYDown.degs)
    gc.setStroke(toFxColor(ead.colour))
    gc.stroke()
  }

  override def tlCircleFill(cf: CircleFill): Unit =
  { setFill(cf.fill)
    gc.fillOval(cf.cenX - cf.radius, cf.cenY - cf.radius, cf.diameter, cf.diameter)
  }

  override def tlCircleFillRadial(circle: Circle, fill: FillRadial): Unit =
  { import paint._
    val stop1 = new Stop(0, toFxColor(fill.cenColour))
    val stop2 = new Stop(1, toFxColor(fill.outerColour))
    val lg1 = new RadialGradient(0, 0, 0.5, 0.5, 0.8, true, CycleMethod.NO_CYCLE, stop1, stop2)
    gc.setFill(lg1)
    gc.fillOval(circle.cenX - circle.radius, circle.cenY - circle.radius, circle.diameter, circle.diameter)
  }
  override def tlCircleDraw(cd: CircleDraw): Unit =
  { gc.setLineWidth(cd.lineWidth)
    gc.setStroke(toFxColor(cd.lineColour))
    gc.strokeOval(cd.cenX - cd.radius, cd.cenY - cd.radius, cd.diameter, cd.diameter)
  }

  override def tlEllipseFill(ef: EllipseFill): Unit =
  { setFill(ef.fill)
    gc.fillOval(ef.cenX - ef.shape.radius1, ef.cenY - ef.shape.radius2 , ef.shape.diameter1, ef.shape.diameter2)
  }

  override def tlEllipseDraw(ed: EllipseDraw): Unit =
  { gc.setLineWidth(ed.lineWidth)
    gc.setStroke(toFxColor(ed.lineColour))
    gc.strokeOval(ed.cenX - ed.shape.radius1, ed.cenY - ed.shape.radius2, ed.shape.diameter1, ed.shape.diameter2)
  }    
    
  override protected[this] def tlDashedLineDraw(dld: DashedLineDraw): Unit =
  { gc.beginPath
    gc.moveTo(dld.xStart, dld.yStart)
    gc.lineTo(dld.xEnd, dld.yEnd)
    gc.setStroke(toFxColor(dld.colour))
    gc.setLineWidth(dld.lineWidth)
    gc.setLineDashes(dld.dashArr :_*)
    gc.stroke()
    gc.setLineDashes()
  }

  def fxAlign(align: TextAlign) =
  { import text._
    align match
    { case CenAlign => TextAlignment.CENTER
      case LeftAlign => TextAlignment.LEFT
      case RightAlign => TextAlignment.RIGHT
    }
  }

  def fxBaseline(baseLine: BaseLine) =
  {
    import BaseLine._
    baseLine match
    { case Top => geometry.VPos.TOP
      case Middle => geometry.VPos.CENTER
      case Alphabetic => geometry.VPos.BASELINE
      case Bottom => geometry.VPos.BOTTOM
      case Hanging => geometry.VPos.TOP
      case Ideographic => geometry.VPos.BASELINE
    }
  }

  override protected[this] def tlBezierDraw(bd: BezierDraw): Unit =
  { gc.setStroke(toFxColor(bd.colour))
    gc.setLineWidth(bd.lineWidth)
    gc.beginPath
    gc.moveTo(bd.xStart, bd.yStart)
    gc.bezierCurveTo(bd.xC1, bd.yC1, bd.xC2, bd.yC2, bd.xEnd, bd.yEnd)
    gc.stroke()
  }
   
// baseline - VPos with values of Top, Center, Baseline, or Bottom or null.

  override protected[this] def tlTextGraphic(tg: TextGraphic): Unit =
  { gc.setTextAlign(fxAlign(tg.align))
    gc.setTextBaseline(fxBaseline(tg.baseLine))
    gc.setFont(new text.Font(tg.fontSize))
    gc.setFill(toFxColor(tg.colour))
    gc.fillText(tg.str, tg.posn.x, tg.posn.y)
  }
   
  protected[this] def tlLinesDraw(lsd: LinesDraw): Unit =
  { gc.beginPath
    lsd.lines.foreach(ls => { gc.moveTo(ls.startX, ls.startY);  gc.lineTo(ls.endX, ls.endY)})
    gc.setLineWidth(lsd.lineWidth)
    gc.setStroke(toFxColor(lsd.colour))
    gc.stroke()
  }
   
  override protected[this] def tlTextOutline(to: TextOutline): Unit =
  { gc.setTextAlign(text.TextAlignment.CENTER)
    gc.setTextBaseline(fxBaseline(to.baseLine))
    gc.setStroke(toFxColor(to.colour))
    gc.setLineWidth(1)
    gc.setFont(new text.Font(to.fontSize))
    gc.strokeText(to.str, to.posn.x, to.posn.y)
  }
   
  private[this] def segsPath(sgo: ShapeGenOld): Unit =
  { gc.beginPath
    var startPt = sgo.segLast.pEnd
    gc.moveTo(startPt.x, startPt.y)
    sgo.dataForeach{ seg =>
      seg.segDo(ls => gc.lineTo(ls.xEnd, ls.yEnd),
        as => as.fControlEndRadius(startPt, gc.arcTo),
        bs => gc.bezierCurveTo(bs.xC1, bs.yC1, bs.xUses, bs.yUses, bs.xEnd, bs.yEnd)
      )
      startPt = seg.pEnd
    }
    gc.closePath
  }
   
  override protected[this] def tlShapeFill(sgf: ShapeGenFillOld): Unit =
  { segsPath(sgf.shape)
    gc.setFill(toFxColor(sgf.colour))
    gc.fill()
  }

  override def tlShapeDraw(sgd: ShapeGenDrawOld): Unit =
  { segsPath(sgd.shape)
    gc.setLineWidth(sgd.lineWidth)
    gc.setStroke(toFxColor(sgd.lineColour))
    gc.stroke()
  }

  override def clear(colour: Colour): Unit =
  { gc.setFill(toFxColor(colour))
    gc.fillRect(0, 0, width, height)
  }

  def getTime: Long = System.currentTimeMillis
  import animation._
  override def timeOut(f: () => Unit, millis: Integer): Unit = new Timeline(new KeyFrame(util.Duration.millis(millis.doubleValue()),
    (ae: event.ActionEvent) => f())).play
   
  override protected[this] def tlClip(poly: Polygon): Unit =
  { gc.beginPath
    gc.moveTo(poly.v0x, poly.v0y)
    poly.vertPairsTailForeach(gc.lineTo)
    gc.closePath()
    gc.clip()
  }

  override def gcSave(): Unit = gc.save()
  override def gcRestore(): Unit = gc.restore()
  def saveFile(fileName: String, output: String): Unit = saveRsonFile(yourDir, fileName, output: String)
  def loadFile(fileName: String): EMon[String] = loadRsonFile(yourDir -/- fileName)
}