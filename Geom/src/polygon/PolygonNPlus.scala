/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A [[Polygon]] with at least 3 vertices. The PolygonNPlus traits include values for the vertices and the x and y components of the vertices. The X
 * and Y components are included because Graphics implementation APIs use them. */
trait Polygon3Plus extends Polygon
{ /** The X component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v1x: Double

  /** The Y component of the 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v1y: Double

  /** The 2nd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v1: Pt2

  /** The X component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v2x: Double

  /** The Y component of the 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v2y: Double

  /** The 3rd Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v2: Pt2

  /** The last vertex. */
  override def vLast: Pt2 = vert(vertsNum - 1)

  /** Polygon side 0 from the last vertex to Vertex 0. */
  def sd0: LineSeg = LineSeg(vLast, v0)

  /** Polygon side 1 from vertex 0 to vertex 1. */
  def sd1: LineSeg = LineSeg(v0, v1)

  /** Polygon side 3 from v2 to v3. */
  def sd2: LineSeg = LineSeg(v1, v2)

  /** The centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can be thought of as
   *  vertex 0.5. */
  def sd1Cen: Pt2

  /** The X component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   *  be thought of as vertex 0.5. */
  def sd1CenX: Double

  /** The Y component of the centre or half way point of side 1 of this polygon. Side 1 starts at the vLast vertex and ends at the v1 vertex. This can
   *  be thought of as vertex 0.5. */
  def sd1CenY: Double

  /** The centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. This can be thought of as
   * vertex 1.5 */
  def sd2Cen: Pt2

  /** The X component of the centre or half way point of side 2 of this polygon. Side 2 starts at the v1 vertex and ends at the v1 vertex. This can be
   *  thought of as vertex 1.5. */
  def sd2CenX: Double

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 2 starts at the v1 vertex and ends at the v2 vertex. This can be
   *  thought of as vertex 1.5. */
  def sd2CenY: Double

  /** The centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be thought of as
   *  vertex 2.5. */
  def sd3Cen: Pt2

  /** The X component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   *  thought of as vertex 2.5. */
  def sd3CenX: Double

  /** The Y component of the centre or half way point of side 3 of this polygon. Side 3 starts at the v2 vertex and ends at the v3 vertex. This can be
   *  thought of as vertex 2.5. */
  def sd3CenY: Double
}

/** A [[Polygon]] with at least 4 vertices. */
trait Polygon4Plus extends Polygon3Plus
{
  /** The X component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v3x: Double
  /** The Y component of the 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v3y: Double
  /** The 4th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise from
   *  12 o'clock. */
  def v3: Pt2

  /** The centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be thought of as
   * vertex 3.5. */
  def sd4Cen: Pt2

  /** The X component of the centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be
   *  thought of as vertex 3.5. */
  def sd4CenX: Double

  /** The Y component of the centre or half way point of side 4 of this polygon. Side 4 starts at the v3 vertex and ends at the v4 vertex. This can be
   *  thought of as vertex 3.5. */
  def sd4CenY: Double
}

/** A [[Polygon]] with at least 5 vertices. */
trait Polygon5Plus extends Polygon4Plus
{
  /** The X component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v5x: Double

  /** The Y component of the 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v5y: Double

  /** The 5th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v5: Pt2

  /** The centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be thought of as
   * vertex 4.5. */
  def sd5Cen: Pt2

  /** The X component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  def sd5CenX: Double

  /** The Y component of the centre or half way point of side 5 of this polygon. Side 5 starts at the v4 vertex and ends at the v5 vertex. This can be
   *  thought of as vertex 4.5. */
  def sd5CenY: Double
}

/** A [[Polygon]] with at least 6 vertices. */
trait Polygon6Plus extends Polygon5Plus
{
  /** The X component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def v6x: Double

  /** The Y component of the 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex
   *  immediately clockwise from 12 o'clock. */
  def y6: Double

  /** The 6th Vertex. The default convention is for the vertices to be numbered in a clockwise direction with the 1st vertex immediately clockwise
   *  from 12 o'clock. */
  def v6: Pt2

  /** The centre or half way point of side 6 of this polygon. Side 6 starts at the v5 vertex and ends at the v6 vertex. This can be thought of as
   * vertex 5.5. */
  def sd6Cen: Pt2

  /** The X component of the centre or half way point of side 6 of this polygon. Side 6 starts at the v5 vertex and ends at the v6 vertex. This can be
   *  thought of as vertex 5.5. */
  def sd6CenX: Double

  /** The Y component of the centre or half way point of side 6 of this polygon. Side 5 starts at the v5 vertex and ends at the v6 vertex. This can be
   *  thought of as vertex 5.5. */
  def sd6CenY: Double
}