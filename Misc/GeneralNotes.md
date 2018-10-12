This is just a place to put various notes, so as stuff doesn't get lost. It can be sorted into proper documentation later.

So its been one of my aspirations to reduce if not remove dependance on scala.Any. Any often leads to extra boxing. Single boxing of AnyRef classes
but double boxing of primitives such Int, Double and Boolean. I created an Opt class as a replacement for Option and started replacing 
scala.Either[+A, +B] with a lower head Error Monad replacement. However this creates similar but possibly worse method signiture complication than
can scala.collection.generic.CanBuildFrom.

The Opt class certainly works for the A* pathfinding algorithm where it doesn't into the user interface.
