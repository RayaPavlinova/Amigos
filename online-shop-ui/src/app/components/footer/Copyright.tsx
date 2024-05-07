import React from 'react';
import { Typography, useTheme } from '@mui/material';
import Grid from '@mui/material/Grid';
import { getTranslation } from '../../../i18n/i18n';
import { useAppSelector } from '../../hooks';

const Copyright = (props: any) => {
    const lang = useAppSelector(state => state.lang.lang);
    const theme = useTheme();

    return (
        <Grid container direction="row" style={{ marginTop: "50px" }} sx={{ mt: "4%", p: "1%", bottom: 0, top: "100vh", position: "sticky" }} bgcolor={theme.palette.mode === "light" ? "#1976d2" : "#272727"} data-testid="footer">
            <Grid item width="34%" ml="16%" sx={{display: 'flex', flexDirection: "column", justifyContent: "space-between"}}>
                <Typography variant="h5" color="#FFF" align="left" sx={{ lineHeight: "1", fontWeight: 600 }}>{getTranslation(lang, "online_shop")}</Typography>
                <Typography variant="subtitle2" color="#FFF" align="left" sx={{ lineHeight: "1" }}>AMIGOS@shop.com<br />555 555 555</Typography>
                <Typography variant="subtitle2" color="#FFF" align="left" sx={{ lineHeight: "1" }}>TEAM AMIGOS, SOFIA</Typography>
            </Grid>
            <Grid item width="34%" mr="16%">
                <Typography variant="subtitle2" color="#FFF" align="right" sx={{ lineHeight: "1", textAlign: "justify" }}>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum iaculis lacus sit amet ipsum finibus blandit. Nam ac nibh et ante varius fermentum sed et turpis. Sed nibh felis, accumsan vel erat in, facilisis vulputate velit. Phasellus ac iaculis eros. Praesent porttitor neque non augue porta facilisis. Nunc hendrerit massa dui, non auctor enim placerat vitae. Aliquam at diam neque.
                    Nam vitae porta nunc. Quisque a nisi nec felis sagittis condimentum. Ut mollis lectus magna, eget maximus turpis rutrum venenatis, odit rutrum.</Typography>
                <Typography variant="subtitle2" color="#FFF" align="right" sx={{ lineHeight: "1" }} marginTop="4%">© 2024</Typography>
            </Grid>
        </Grid>
    );
}

export default Copyright;