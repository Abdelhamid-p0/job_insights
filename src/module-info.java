module Model {
    requires java.base; 
    requires java.sql;
	requires weka.cleaned;
    exports Model;  // Export your Model package to be used by other modules
}
