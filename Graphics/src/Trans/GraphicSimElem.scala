/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom

/** A DisplayElem for which all leaf elements of this trait maintain their type through all similar transformation. This type is purely for the 
 * convenience of using the fTrans method to perform all the Similar transformations. It is not a useful user type hence it has no type class
 * instances associated with it. */
trait GraphicSimElem extends SimilarPreserve with GraphicElem
{ type ThisT <: GraphicSimElem
}

/** A DisplayElem for which all leaf elements of this trait maintain their type through all affine transformation. This type is purely for the 
 * convenience of using the fTrans method to perform all the affine transformations. It is not a useful user type hence it has no type class
 * instances associated with it. */
trait GraphicAffineElem extends GraphicSimElem with AffinePreserve
{ type ThisT <: GraphicAffineElem
}