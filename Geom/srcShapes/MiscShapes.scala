/* Copyright 2018-21 Richard Oliver, W0d. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

object Star5
{
  val classicRatio = 0.382

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Pt2Arr = Pt2Arr(Pt2(0, 1), Pt2(0, ratio).rotateDegs(36))
    iToFlatMap(4)(i => l2.rotate(-DegVec72 * i)).toPolygon
  }

  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fill(colour)
  
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(lineWidth, colour)

  def crossLines(ratio: Double = classicRatio): GraphicElem =
  { val poly = apply(ratio)
    IntArr(1, 5, 9, 3, 7, 1).mapLinePath(poly.vert(_)).draw()//.toPathDraw(1)
  }

  /** Not quite sure what this does. */
  def ptUpYCentred(ratio: Double = classicRatio): Polygon = apply(ratio).slateY(DegVec36.cos / 2 - 0.5)
}

object Star7
{
  val classicRatio = 0.692

  def apply(ratio: Double = classicRatio): Polygon =
  { val l2: Pt2Arr = Pt2Arr(Pt2(0, 1), Pt2(0, ratio).rotateClkDegs(180 / 7))
    iToFlatMap(6)(i => l2.rotate(AngleVec(-360 / 7) * i)).toPolygon
  }
  
  def fill(colour: Colour, ratio: Double = classicRatio): PolygonFill = apply(ratio).fill(colour)
}

object Star3
{
  def apply(sideLength: Double = 1): PolygonGen = PolygonGen((0 pp sideLength * math.sqrt(3) / 3),
              (sideLength / 2 pp -sideLength * math.sqrt(3) / 6),
              (-sideLength / 2 pp -sideLength * math.sqrt(3) / 6))
      
  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(lineWidth, colour)

  def fill(colour: Colour): PolygonFill = apply().fill(colour)
}

object Pentagram
{
  def apply(): PolygonGen =
  { val l2: Pt2Arr = Pt2Arr(Pt2(0, 1), Pt2(0, -1).rotateClkDegs(36))
    iToFlatMap(4)(i => l2.rotate(-DegVec72 * i)).toPolygon
  }

  def draw(lineWidth: Double = 1, colour: Colour): PolygonDraw = apply().draw(lineWidth, colour)
}