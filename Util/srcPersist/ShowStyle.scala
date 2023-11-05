/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

/** Currently can't think of a better name for this trait */
sealed trait ShowStyle

/** Show the object just as its comma separated constituent values. */
case object ShowCommas extends ShowStyle

/** Show the object as semicolon separated constituent values. */
case object ShowSemis extends ShowStyle

/** Show the object in the standard default manner. */
case object ShowStandard extends ShowStyle

/** Show the object in the standard default manner, with parameter names. */
case object ShowFieldNames extends ShowStyle

/** Show the object as semicolon separated constituent values preceded b y their parameter names. */
case object ShowSemisNames extends ShowStyle

/** Show the object in the standard default manner, with field names and their types. */
case object ShowStdTypedFields extends ShowStyle

/** Show the object with the type of the object even if the string representation does not normally states its type. Eg Int(7). */
case object ShowTyped extends ShowStyle

/** Represents the object with an underscore. */
case object ShowUnderScore extends ShowStyle