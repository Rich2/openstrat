/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** A convenience trait type class trait for persistence, that combines the [[ShowPrecisionT]] and [[UnShow]] type classes. Most if not all final classes that
 * inherit from this trait will require type class instances of ShowT and UnShowT to implement [[PersistPrec]]'s members. It is most important that these
 * implicit parameter instances be specified as separate ShowT and UnShowT parameters. Do not combine them into a Persist parameter. There are no
 * implicit instances for [[Int]], [[Double]], [[List]] etc in the [[PersistPrec]] companion object, the Persist components for these standard types will
 * be found in the ShowT and UnShow companion objects. */
trait Persist[T] extends ShowT[T] with UnShow[T]

/** A convenience trait type class trait for persistence, that combines the [[ShowPrecisionT]] and [[UnShow]] type classes. Most if not all final classes that
 * inherit from this trait will require type class instances of ShowT and UnShowT to implement [[PersistPrec]]'s members. It is most important that these
 * implicit parameter instances be specified as separate ShowT and UnShowT parameters. Do not combine them into a Persist parameter. There are no
 * implicit instances for [[Int]], [[Double]], [[List]] etc in the [[PersistPrec]] companion object, the Persist components for these standard types will
 * be found in the ShowT and UnShow companion objects. */
trait PersistPrec[T] extends ShowPrecisionT[T] with UnShow[T] with Persist[T]