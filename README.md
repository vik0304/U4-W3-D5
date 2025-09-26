Database per biblioteca.

Il progetto presenta tabella checkout, tabella user e tabelle magazine e book che ereditano dalla classe padre element. Per gestire l'ereditarietà ho scelto table_per_class in quanto ho spesso bisogno di accedere ai dati di
book e magazine e in questo modo ognuno sta nella propria tabella con i prorpi dati, element rimane ovviamente come classe astratta non avendo bisogno di aggiungere nessun elemento che non è rivista o libro. Checkout è stata 
pensata come una sorta di conjunction table tra user e elemento difatti presenta relazioni manytoone sia con element che con user. 
