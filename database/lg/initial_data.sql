SELECT * FROM public.roles;
SELECT * FROM public.genders;


DELETE FROM public.roles WHERE id = 3;

-- Initial roles
INSERT INTO public.roles (description) VALUES ('admin');
INSERT INTO public.roles (description) VALUES ('default');


-- Initial genders
INSERT INTO public.genders (description) VALUES ('male');
INSERT INTO public.genders (description) VALUES ('female');
INSERT INTO public.genders (description) VALUES ('other');
