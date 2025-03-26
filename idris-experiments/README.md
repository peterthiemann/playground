## Borrowed session types in Idris2

* requires `idris2` with library `linear` installed in the path
* to compile, run the bash script `compile.sh`

## Files

* `Linear.idr` patched version of the like-named file from the Idris2 library `linear`. Patches by the maintainer.
* `LinearUnion.idr` patched version of the library file `Data.OpenUnion.idr` adapted by us to accept linear types.
* `Sessionx.idr` patched version of the session library https://test.idris-lang.org/Idris2/linear/source/System.Concurrency.Session.html to accommodate transmitting linear types.
* `Splittablex.idr` our implementation of splittable channels as documented in the paper. The functions are named as in the paper and the remaining definitions are commented.

