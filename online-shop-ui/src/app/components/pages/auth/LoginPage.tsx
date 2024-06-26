import * as React from 'react';
import Link from '@mui/material/Link';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import { Link as RouterLink, useNavigate } from 'react-router-dom';
import { Snackbar, Alert } from '@mui/material';

import UserInputFields from './UserInputFields';
import * as UserRequest from '../../../api/UserRequest';
import * as AuthRequest from '../../../api/AuthRequest';
import { InputError } from '../../../utils/InputErrorUtils';

import { useAppDispatch, useAppSelector } from '../../../hooks';
import { updateJwt } from '../../../slices/jwtSlice';
import { updateUsername } from '../../../slices/usernameSlice';
import { parseJwt } from '../../../utils/Utils';
import { updateInfo } from '../../../slices/infoSlice';
import { getTranslation } from '../../../../i18n/i18n';

const LoginPage = () => {
  const lang = useAppSelector(state => state.lang.lang);

  const [inputErrors, setInputErrors] = React.useState<InputError[]>([]);
  const [error, setError] = React.useState("");
  const navigate = useNavigate();

  const dispatch = useAppDispatch();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setInputErrors([]);
    setError("");

    const data = new FormData(event.currentTarget);
    let username = data.get('username')?.toString();
    let password = data.get('password')?.toString();

    const response = await AuthRequest.login(username ?? "", password ?? "");
    if (response.error) {
      setError(response.error);
    } else if (response.accessToken) {
      dispatch(updateJwt(response.accessToken));
      dispatch(updateUsername(parseJwt(response.accessToken).sub));
      dispatch(updateInfo(await UserRequest.getUserInfo(username ?? "")));
      navigate("/products");
    } else {
      response.fieldErrors && setInputErrors(response.fieldErrors);
    }
  };

  const handleAlertClick = () => {
    setError("");
  };

  return (
    <Container component="main" maxWidth="xs" sx={{ pt: 26 }}>
      {error.length > 0 &&
        <Snackbar open={error.length > 0} autoHideDuration={2000} onClose={handleAlertClick}>
          <Alert data-testid="alert-error" onClose={handleAlertClick} severity="error" sx={{ width: '100%' }} id="alert-error">
            {getTranslation(lang, error)}
          </Alert>
        </Snackbar>}
      <Grid container>
        <Grid item>
          <UserInputFields title={getTranslation(lang, "sign_in")} buttonText={getTranslation(lang, "sign_in")} handleSubmit={handleSubmit} isEmailAndRoleMandatory={false} inputErrors={inputErrors} />
        </Grid>
        <Grid item>
          <Link component={RouterLink} to={'/register'} variant="body2" id="dont-have-account">
            {getTranslation(lang, "create_account_message")}
          </Link>
        </Grid>
      </Grid>
    </Container>
  );
}

export default LoginPage;