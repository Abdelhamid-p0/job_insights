module Model {
    requires java.base; 
    requires java.sql;
	requires weka.cleaned;
    requires java.desktop;
    exports Model;  // Export your Model package to be used by other modules
}
